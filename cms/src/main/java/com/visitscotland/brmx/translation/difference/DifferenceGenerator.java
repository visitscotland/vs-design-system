package com.visitscotland.brmx.translation.difference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.translation.plugin.JcrDocument;
import com.visitscotland.brmx.translation.plugin.JcrDocumentFactory;
import org.hippoecm.editor.template.JcrTemplateStore;
import org.hippoecm.editor.type.JcrTypeLocator;
import org.hippoecm.frontend.model.ocm.StoreException;
import org.hippoecm.frontend.plugin.config.IClusterConfig;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.types.IFieldDescriptor;
import org.hippoecm.frontend.types.ITypeDescriptor;
import org.hippoecm.repository.api.HippoNode;
import org.hippoecm.repository.api.HippoNodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jcr.*;
import java.io.IOException;
import java.util.*;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class DifferenceGenerator {
    private static final Logger logger = LoggerFactory.getLogger(DifferenceGenerator.class);

    private Session jcrSession;
    private JcrDocumentFactory jcrDocumentFactory;
    private JcrTypeLocator jcrTypeLocator;
    private JcrTemplateStore jcrTemplateStore;
    private ObjectMapper objectMapper;

    @Autowired
    public DifferenceGenerator(Session jcrSession,
                               JcrDocumentFactory jcrDocumentFactory,
                               JcrTypeLocator jcrTypeLocator,
                               JcrTemplateStore jcrTemplateStore,
                               ObjectMapper objectMapper) {
        this.jcrSession = jcrSession;
        this.jcrDocumentFactory = jcrDocumentFactory;
        this.jcrTypeLocator = jcrTypeLocator;
        this.jcrTemplateStore = jcrTemplateStore;
        this.objectMapper = objectMapper;
    }

    public String getTranslationDifferenceJson(String nodeId) throws IOException, RepositoryException, StoreException {
        return objectMapper.writer().writeValueAsString(getTranslationDifference(nodeId));
    }

    /**
     * Given the Node ID of a handle, or a variant, will look for differences between the published and unpublished
     * variants of the document.
     *
     * @param nodeId The Node ID of the document handle, or one of it's variants.
     * @return
     */
    public DocumentDifference getTranslationDifference(String nodeId) throws RepositoryException, StoreException {
        Node node = jcrSession.getNodeByIdentifier(nodeId);
        JcrDocument document = jcrDocumentFactory.createFromNode(node);
        Node unpublished = document.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
        String primaryNodeType = unpublished.getPrimaryNodeType().getName();
        ITypeDescriptor typeDescriptor = jcrTypeLocator.locate(primaryNodeType);
        List<IFieldDescriptor> fieldDescriptorList = buildIFieldDescriptorList(typeDescriptor);
        Map<String, String> captionMap = buildFieldNameToCaptionMap(typeDescriptor);
        return buildDocumentDifferences(document, fieldDescriptorList, captionMap);
    }

    protected List<IFieldDescriptor> buildIFieldDescriptorList(ITypeDescriptor typeDescriptor) throws StoreException {
        List<IFieldDescriptor> fieldDescriptors = new ArrayList<>();
        // The gets a Node's fields declared in its type but not its super types
        fieldDescriptors.addAll(typeDescriptor.getDeclaredFields().values());
        List<String> superTypes = typeDescriptor.getSuperTypes();
        // Iterate over the super types adding the declared fields from our namespace
        for (String superType : superTypes) {
            if (superType.startsWith("visitscotland:")) {
                ITypeDescriptor superTypeDesc = jcrTypeLocator.locate(superType);
                fieldDescriptors.addAll(buildIFieldDescriptorList(superTypeDesc));
            }
        }
        return fieldDescriptors;
    }

    protected Map<String, String> buildFieldNameToCaptionMap(ITypeDescriptor typeDescriptor) {
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("type", typeDescriptor);
        Iterator<IClusterConfig> iClusterConfigIterator = jcrTemplateStore.find(criteria);
        Map<String, String> captionMap = new HashMap<>();
        if (!iClusterConfigIterator.hasNext()) {
            throw new RuntimeException("Missing config");
        } else {
            IClusterConfig config = iClusterConfigIterator.next();
            List<IPluginConfig> pluginList = config.getPlugins();
            for ( IPluginConfig plugin : pluginList ) {
                String fieldName = (String)plugin.get("field");
                String caption = (String)plugin.get("caption");
                if (fieldName != null && caption != null) {
                    captionMap.put(fieldName, caption);
                }
            }
        }
        return captionMap;
    }

    protected DocumentDifference buildDocumentDifferences(JcrDocument document,
                                                          List<IFieldDescriptor> fieldDescriptorList,
                                                          Map<String, String> captionMap) throws RepositoryException, StoreException {
        Node unpublishedNode = document.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
        // liveNode could be null if it has not yet been created.
        Node liveNode = document.getVariantNode(JcrDocument.VARIANT_PUBLISHED);

        DocumentDifference docDiff = new DocumentDifference();
        for (IFieldDescriptor field : fieldDescriptorList) {
            try {
                FieldDifference diff = new FieldDifference();
                diff.setCaption(captionMap.get(field.getName()));
                diff.setProperty(field.getName());

                Field latestValue = null;
                Field liveValue = null;

                if (field.getTypeDescriptor().isNode()) {
                    latestValue = buildNodeField(field, unpublishedNode);
                    if (null != liveNode) {
                        liveValue = buildNodeField(field, liveNode);
                    } else {
                        liveValue = buildEmptyField(field);
                    }
                } else {
                    latestValue = buildPropertyField(field, unpublishedNode);
                    if (null != liveNode) {
                        liveValue = buildPropertyField(field, liveNode);
                    } else {
                        liveValue = buildEmptyField(field);
                    }
                }
                diff.setLatest(latestValue);
                diff.setPrevious(liveValue);
                if (!latestValue.equals(liveValue)) {
                    docDiff.getDifferences().add(diff);
                }
            } catch(ValueFormatException ex) {
                // This means we have tried to convert something to String that cannot be converted
                logger.warn("Unable to get diff value", ex);
            }
        }
        return docDiff;
    }

    protected Field buildNodeField(IFieldDescriptor fieldDescriptor, Node node) throws RepositoryException, StoreException {
        NodeIterator nodeIterator = node.getNodes(fieldDescriptor.getPath());
        if (fieldDescriptor.isMultiple()) {
            List<FieldValue> nodeValueList = new ArrayList<>();
            if (!nodeIterator.hasNext()) {
                return buildEmptyField(fieldDescriptor);
            }
            while(nodeIterator.hasNext()) {
                Node propertyNode = nodeIterator.nextNode();
                FieldValue nodeValue = getNodeValue(propertyNode);
                nodeValueList.add(nodeValue);
            }
            MultipleField fieldValue = new MultipleField();
            fieldValue.setField(nodeValueList);
            return fieldValue;
        } else {
            if (nodeIterator.hasNext()) {
                Node propertyNode = nodeIterator.nextNode();
                SingleField fieldValue = new SingleField();
                fieldValue.setField(getNodeValue(propertyNode));
                return fieldValue;
            } else {
                return buildEmptyField(fieldDescriptor);
            }
        }
    }

    protected FieldValue getNodeValue(Node node) throws RepositoryException, StoreException {
        // If node has a docbase, then treat it as a link. The node id is the value,
        // the display name of the linked node is the caption
        if (node.hasProperty(HippoNodeType.HIPPO_DOCBASE)) {
            Property docbase = node.getProperty(HippoNodeType.HIPPO_DOCBASE);
            FieldValue value = new FieldValue();
            value.setDocBase(docbase.getString());
            try {
                HippoNode linkedNode = (HippoNode) jcrSession.getNodeByIdentifier(docbase.getString());
                value.setValue(linkedNode.getDisplayName());
            } catch(ItemNotFoundException ex) {
                throw new ValueFormatException("Missing linked node");
            }
            return value;
        }

        // If the node has a primary field use that as the value for the node.
        IFieldDescriptor primaryField = getPrimaryField(node);
        if (null != primaryField) {
            Property primaryProperty = node.getProperty(primaryField.getPath());
            FieldValue value = new FieldValue();
            value.setValue(primaryProperty.getString());
            return value;
        }

        // The node does not match what we know, throw a ValueFormatException
        throw new ValueFormatException("Unknown node type, unable to diff");
    }

    protected IFieldDescriptor getPrimaryField(Node node) throws RepositoryException, StoreException {
        ITypeDescriptor typeDescriptor = jcrTypeLocator.locate(node.getPrimaryNodeType().getName());
        List<IFieldDescriptor> fieldDescriptorList = buildIFieldDescriptorList(typeDescriptor);
        for (IFieldDescriptor field : fieldDescriptorList) {
            if (field.isPrimary()) {
                return field;
            }
        }
        return null;
    }

    protected Field buildEmptyField(IFieldDescriptor field) {
        if(field.isMultiple()) {
            MultipleField fieldValue = new MultipleField();
            fieldValue.setField(Collections.emptyList());
            return fieldValue;
        } else {
            SingleField fieldValue = new SingleField();
            fieldValue.setField(new FieldValue(""));
            return fieldValue;
        }
    }

    protected Field buildPropertyField(IFieldDescriptor field, Node node) throws PathNotFoundException, ValueFormatException, RepositoryException {
        try {
            Property nodeProperty = node.getProperty(field.getPath());
            if (field.isMultiple()) {
                List<FieldValue> values = new ArrayList<>();
                Value[] valueArray = nodeProperty.getValues();
                for (Value val : valueArray) {
                    values.add(new FieldValue(val.getString()));
                }
                MultipleField fieldValue = new MultipleField();
                fieldValue.setField(values);
                return fieldValue;
            } else {
                SingleField fieldValue = new SingleField();
                fieldValue.setField(new FieldValue(nodeProperty.getString()));
                return fieldValue;
            }
        } catch(PathNotFoundException ex) {
            return buildEmptyField(field);
        }
    }
}
