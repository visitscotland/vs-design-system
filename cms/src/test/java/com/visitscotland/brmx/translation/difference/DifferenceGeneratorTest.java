package com.visitscotland.brmx.translation.difference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.translation.IFieldDescriptorBuilder;
import com.visitscotland.brmx.translation.ITypeDescriptorBuilder;
import com.visitscotland.brmx.translation.JcrDocumentBuilder;
import com.visitscotland.brmx.translation.NodeBuilder;
import com.visitscotland.brmx.translation.plugin.JcrDocument;
import com.visitscotland.brmx.translation.plugin.JcrDocumentFactory;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.assertj.core.api.Condition;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeMatcher;
import org.hippoecm.editor.template.JcrTemplateStore;
import org.hippoecm.editor.type.JcrTypeLocator;
import org.hippoecm.frontend.model.ocm.StoreException;
import org.hippoecm.frontend.plugin.config.IClusterConfig;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.types.IFieldDescriptor;
import org.hippoecm.frontend.types.ITypeDescriptor;
import org.hippoecm.repository.api.HippoNode;
import org.hippoecm.repository.api.HippoNodeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import javax.jcr.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DifferenceGeneratorTest {
    @Mock
    private Session mockJcrSession;
    @Mock
    private JcrDocumentFactory mockJcrDocumentFactory;
    @Mock
    private JcrTypeLocator mockJcrTypeLocator;
    @Mock
    private JcrTemplateStore mockJcrTemplateStore;
    private Map<ITypeDescriptor, List<IClusterConfig>> templateStoreMap;
    @Mock
    private ObjectMapper mockObjectMapper;

    private DifferenceGenerator generator;

    @BeforeEach
    public void beforeEach() {
        templateStoreMap = new HashMap<>();
        generator = new DifferenceGenerator(
                mockJcrSession,
                mockJcrDocumentFactory,
                mockJcrTypeLocator,
                mockJcrTemplateStore,
                new ObjectMapper());
        lenient().doAnswer(new Answer<Iterator<IClusterConfig>>() {
            @Override
            public Iterator<IClusterConfig> answer(InvocationOnMock invocation) throws Throwable {
                Map<String, Object> criteria = invocation.getArgument(0);
                ITypeDescriptor typeToFind = (ITypeDescriptor)criteria.get("type");
                List<IClusterConfig> configList = null;
                if (null != typeToFind) {
                    configList = templateStoreMap.get(typeToFind);
                }
                if (null == configList){
                    return Collections.<IClusterConfig>emptyList().iterator();
                } else {
                    return configList.iterator();
                }
            }
        }).when(mockJcrTemplateStore).find(any(Map.class));
    }

    @Test
    @DisplayName("buildIFieldDescriptorList - No declared fields or super types")
    public void buildIFieldDescriptor() throws Exception {
        // When there are no declared fields or super types should just return an empty list, not null
        ITypeDescriptor mockTypeDescriptor = mock(ITypeDescriptor.class);
        when(mockTypeDescriptor.getDeclaredFields()).thenReturn(Collections.emptyMap());
        when(mockTypeDescriptor.getSuperTypes()).thenReturn(Collections.emptyList());

        List<IFieldDescriptor> descriptorList = generator.buildIFieldDescriptorList(mockTypeDescriptor);

        assertThat(descriptorList).isNotNull();
        assertThat(descriptorList).isEmpty();
    }

    @Test
    @DisplayName("buildIFieldDescriptorList - With declared fields only")
    public void buildIFieldDescriptor_withDeclaredFields() throws Exception {
        // When there are declared fields they should be in the list
        ITypeDescriptor mockTypeDescriptor = new ITypeDescriptorBuilder()
                .withDeclaredFieldMap(
                        createDeclaredFieldMap(
                                new IFieldDescriptorBuilder().withName("field1").build(),
                                new IFieldDescriptorBuilder().withName("field2").build()
                        )
                )
                .build();

        List<IFieldDescriptor> descriptorList = generator.buildIFieldDescriptorList(mockTypeDescriptor);

        assertThat(descriptorList).isNotNull();
        assertThat(descriptorList).hasSize(2);
        List<String> descriptorNames = descriptorList.stream().map(desc -> desc.getName()).collect(Collectors.toList());
        assertThat(descriptorNames).containsExactly("field1", "field2");
    }

    @Test
    @DisplayName("buildIFieldDescriptorList - With super types")
    public void buildIFieldDescriptor_withSuperTypes() throws Exception {
        // When there are super type their declared fields should be added if they are in the visitscotland namespace
        // Should recurse down the super types to include all declared fields
        ITypeDescriptor mockTypeDescriptor = new ITypeDescriptorBuilder()
                .withType("visitscotland:type")
                .withDeclaredFieldMap(
                    createDeclaredFieldMap(
                            new IFieldDescriptorBuilder().withName("field1").build(),
                            new IFieldDescriptorBuilder().withName("field2").build()
                    )
                )
                .withSuperType( new ITypeDescriptorBuilder()
                    .withType("visitscotland:super1")
                    .addToJcrTypeLocator(mockJcrTypeLocator)
                    .withDeclaredFieldMap(
                        createDeclaredFieldMap(
                                new IFieldDescriptorBuilder().withName("super1field1").build(),
                                new IFieldDescriptorBuilder().withName("super1field2").build()
                        )
                    )
                    .withSuperType( new ITypeDescriptorBuilder()
                            .withType("hippo:nested").build()
                    ).build()
                )
                .withSuperType(new ITypeDescriptorBuilder()
                        .withType("visitscotland:super2")
                        .addToJcrTypeLocator(mockJcrTypeLocator)
                        .withDeclaredFieldMap(
                            createDeclaredFieldMap(
                                    new IFieldDescriptorBuilder().withName("super2field1").build()
                            )
                        )
                        .withSuperType(new ITypeDescriptorBuilder()
                                .withType("visitscotland:super3")
                                .addToJcrTypeLocator(mockJcrTypeLocator)
                                .withDeclaredFieldMap(
                                    createDeclaredFieldMap(
                                            new IFieldDescriptorBuilder().withName("super3field1").build()
                                    )
                                ).build()
                        ).build()
                )
                .withSuperType(
                        new ITypeDescriptorBuilder()
                        .withType("hippo:other").build()
                ).build();

        List<IFieldDescriptor> descriptorList = generator.buildIFieldDescriptorList(mockTypeDescriptor);

        assertThat(descriptorList).isNotNull();
        assertThat(descriptorList).hasSize(6);
        List<String> descriptorNames = descriptorList.stream().map(desc -> desc.getName()).collect(Collectors.toList());
        assertThat(descriptorNames)
                .containsExactly("field1", "field2", "super1field1", "super1field2", "super2field1", "super3field1");

    }

    @Test
    @DisplayName("buildFieldNameToCaptionMap - No config for type")
    public void buildFieldNameToCaptionMap_emptyConfig() {
        // When there is no config for the given type a RuntimeException should be thrown
        ITypeDescriptor mockType = mock(ITypeDescriptor.class);
        ITypeDescriptor otherType = mock(ITypeDescriptor.class);
        addTypeToTemplateStore(otherType, createIClusterConfig(createIPluginConfig("field1", "caption1")));

        assertThatThrownBy(() -> generator.buildFieldNameToCaptionMap(mockType)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("buildFieldNameToCaptionMap - With config for type")
    public void buildFieldNameToCaptionMap_withConfig() {
        // When there is config for the given type a Map of field -> caption should be returned.
        ITypeDescriptor mockType = mock(ITypeDescriptor.class);
        addTypeToTemplateStore(mockType, createIClusterConfig(
                createIPluginConfig("field1", "caption1"),
                createIPluginConfig("field2", null),
                createIPluginConfig(null, "caption3"),
                createIPluginConfig("field3", "caption3")
        ));

        Map<String, String> captionMap =  generator.buildFieldNameToCaptionMap(mockType);

        assertThat(captionMap).isNotNull();
        assertThat(captionMap).hasSize(2);
        assertThat(captionMap).containsEntry("field1", "caption1");
        assertThat(captionMap).containsEntry("field3", "caption3");
    }

    @Test
    @DisplayName("buildPropertyField - Convert a multiple property to a Field instance")
    public void buildPropertyField_multipleWithValues() throws Exception {
        // Expect each of the property values to be inserted into the MultipleField instance values
        IFieldDescriptor mockDescriptor = new IFieldDescriptorBuilder()
                .withPath("multiplePropertyPath")
                .isMultiple(true)
                .build();

        Node mockNode = new NodeBuilder()
                .withProperty("multiplePropertyPath",
                    createValues(createValue("value1"), createValue("value2")))
                .build();

        Field propertyField = generator.buildPropertyField(mockDescriptor, mockNode);

        assertThat(propertyField).isInstanceOf(MultipleField.class);

        MultipleField multipleField = (MultipleField)propertyField;
        List<FieldValue> valueList = multipleField.getField();

        assertThat(valueList).hasSize(2);
        assertThat(valueList).containsExactly(
                new FieldValue(null, "value1"),
                new FieldValue(null, "value2")
        );
    }

    @Test
    @DisplayName("buildPropertyField - Convert a multiple property to a Field instance with no values")
    public void buildPropertyField_multipleNoValues() throws Exception {
        // If an empty array is returned for the property we expect the MultipleField to also have
        // an empty set of values
        IFieldDescriptor mockDescriptor = new IFieldDescriptorBuilder()
                .withPath("multiplePropertyPath")
                .isMultiple(true).build();
        Node mockNode = new NodeBuilder().withProperty("multiplePropertyPath", new Value[] {}).build();

        Field propertyField = generator.buildPropertyField(mockDescriptor, mockNode);

        assertThat(propertyField).isInstanceOf(MultipleField.class);

        MultipleField multipleField = (MultipleField)propertyField;
        List<FieldValue> valueList = multipleField.getField();

        assertThat(valueList).isEmpty();
    }

    @Test
    @DisplayName("buildPropertyField - Convert a multiple property to a Field instance, property not found")
    public void buildPropertyField_multipleNoProperty() throws Exception {
        // If the property is not found on the node we want to return an empty MultipleField, i.e there are no values
        IFieldDescriptor mockDescriptor = new IFieldDescriptorBuilder()
                .withPath("multiplePropertyPath")
                .isMultiple(true)
                .build();
        Node mockNode = mock(Node.class);
        when(mockNode.getProperty(any())).thenThrow(new PathNotFoundException());

        Field propertyField = generator.buildPropertyField(mockDescriptor, mockNode);

        assertThat(propertyField).isInstanceOf(MultipleField.class);

        MultipleField multipleField = (MultipleField)propertyField;
        List<FieldValue> valueList = multipleField.getField();

        assertThat(valueList).isEmpty();
    }

    @Test
    @DisplayName("buildPropertyField - Convert a single property to a Field instance")
    public void buildPropertyField_single() throws Exception {
        IFieldDescriptor mockDescriptor = new IFieldDescriptorBuilder()
                .withPath("singlePropertyPath")
                .isMultiple(false)
                .build();
        Node mockNode = new NodeBuilder().withProperty("singlePropertyPath", "value1").build();

        Field propertyField = generator.buildPropertyField(mockDescriptor, mockNode);

        assertThat(propertyField).isInstanceOf(SingleField.class);

        SingleField singleField = (SingleField)propertyField;
        assertThat(singleField.getField()).isEqualTo(new FieldValue(null, "value1"));
    }

    @Test
    @DisplayName("buildPropertyField - Convert a single property to a Field instance, property not found")
    public void buildPropertyField_singleNoProperty() throws Exception {
        IFieldDescriptor mockDescriptor = new IFieldDescriptorBuilder()
                .withPath("singlePropertyPath")
                .isMultiple(false)
                .build();

        Node mockNode = mock(Node.class);
        when(mockNode.getProperty(any())).thenThrow(new PathNotFoundException());

        Field propertyField = generator.buildPropertyField(mockDescriptor, mockNode);

        assertThat(propertyField).isInstanceOf(SingleField.class);

        SingleField singleField = (SingleField)propertyField;
        assertThat(singleField.getField()).isEqualTo(new FieldValue(null, ""));
    }

    @Test
    @DisplayName("getPrimaryField - Get the primary field from a type that has a primary field")
    public void getPrimaryField_withPrimaryField() throws Exception {
        // Should return the first primary field from the type
        Node mockNode = new NodeBuilder().withPrimaryNodeType("mockNodeType").build();

        new ITypeDescriptorBuilder()
                .withType("mockNodeType")
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withDeclaredFieldMap(
                        createDeclaredFieldMap(
                            new IFieldDescriptorBuilder().withName("field1").isPrimary(false).build(),
                            new IFieldDescriptorBuilder().withName("field2").isPrimary(false).build(),
                            new IFieldDescriptorBuilder().withName("field3").isPrimary(false).build(),
                            new IFieldDescriptorBuilder().withName("field4").isPrimary(true).build(),
                            new IFieldDescriptorBuilder().withName("field5").isPrimary(false).build()
                        )
                ).build();


        IFieldDescriptor primaryField = generator.getPrimaryField(mockNode);

        assertThat(primaryField).isNotNull();
        assertThat(primaryField.getName()).isEqualTo("field4");
    }

    @Test
    @DisplayName("getPrimaryField - Get the primary field from a type that has no primary field")
    public void getPrimaryField_noPrimaryField() throws Exception {
        // Should return null when the type has no primary field
        Node mockNode = new NodeBuilder().withPrimaryNodeType("mockNodeType").build();

        new ITypeDescriptorBuilder()
                .withType("mockNodeType")
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withDeclaredFieldMap(
                        createDeclaredFieldMap(
                            new IFieldDescriptorBuilder().withName("field1").isPrimary(false).build(),
                            new IFieldDescriptorBuilder().withName("field2").isPrimary(false).build(),
                            new IFieldDescriptorBuilder().withName("field3").isPrimary(false).build(),
                            new IFieldDescriptorBuilder().withName("field4").isPrimary(false).build(),
                            new IFieldDescriptorBuilder().withName("field5").isPrimary(false).build()
                        )
                ).build();


        IFieldDescriptor primaryField = generator.getPrimaryField(mockNode);

        assertThat(primaryField).isNull();
    }

    @Test
    @DisplayName("getPrimaryField - Get the primary field from a type that has no fields")
    public void getPrimaryField_noField() throws Exception {
        // Should return null when the type has no primary field
        Node mockNode = new NodeBuilder().withPrimaryNodeType("mockNodeType").build();

        new ITypeDescriptorBuilder()
                .withType("mockNodeType")
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withDeclaredFieldMap(
                        createDeclaredFieldMap()
                ).build();

        IFieldDescriptor primaryField = generator.getPrimaryField(mockNode);

        assertThat(primaryField).isNull();
    }

    @Test
    @DisplayName("getNodeValue - Node has no docBase, or primary field")
    public void getNodeValue_noDocBase_andNoPrimaryField() throws Exception {
        Node mockNode = new NodeBuilder().withPrimaryNodeType("mockNodeType").build();

        new ITypeDescriptorBuilder()
                .withType("mockNodeType")
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withDeclaredFieldMap(
                        createDeclaredFieldMap(
                            new IFieldDescriptorBuilder().withName("field1").isPrimary(false).build()
                        )
                ).build();

        assertThatThrownBy(() -> generator.getNodeValue(mockNode)).isInstanceOf(ValueFormatException.class);
    }

    @Test
    @DisplayName("getNodeValue - Node has docBase, but linked node not found")
    public void getNodeValue_withDocBase_nodeNotFound() throws Exception {
        Node mockNode = new NodeBuilder()
                .withProperty(HippoNodeType.HIPPO_DOCBASE, "linkNodeId")
                .build();

        when(mockJcrSession.getNodeByIdentifier(eq("linkNodeId"))).thenThrow(new ItemNotFoundException());

        assertThatThrownBy(() -> generator.getNodeValue(mockNode))
                .isInstanceOf(ValueFormatException.class)
                .hasMessage("Missing linked node");
    }

    @Test
    @DisplayName("getNodeValue - Node has docBase")
    public void getNodeValue_withDocBase() throws Exception {
        Node mockNode = new NodeBuilder()
                .withProperty(HippoNodeType.HIPPO_DOCBASE, "linkNodeId")
                .build();

        HippoNode mockLinkedNode = mock(HippoNode.class);
        when(mockJcrSession.getNodeByIdentifier(eq("linkNodeId"))).thenReturn(mockLinkedNode);
        when(mockLinkedNode.getDisplayName()).thenReturn("linkDisplayName");

        FieldValue nodeValue = generator.getNodeValue(mockNode);

        assertThat(nodeValue.getDocBase()).isEqualTo("linkNodeId");
        assertThat(nodeValue.getValue()).isEqualTo("linkDisplayName");
    }

    @Test
    @DisplayName("getNodeValue - Node with primary field")
    public void getNodeValue_withPrimaryField() throws Exception {
        Node mockNode = new NodeBuilder()
                .withPrimaryNodeType("mockNodeType")
                .withProperty("field1path", "field1value")
                .build();

        new ITypeDescriptorBuilder()
                .withType("mockNodeType")
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withDeclaredFieldMap(
                        createDeclaredFieldMap(
                            new IFieldDescriptorBuilder().withName("field1").withPath("field1path").isPrimary(true).build()
                        )
                ).build();

        FieldValue fieldValue = generator.getNodeValue(mockNode);

        assertThat(fieldValue.getDocBase()).isNull();
        assertThat(fieldValue.getValue()).isEqualTo("field1value");
    }

    @Test
    @DisplayName("buildNodeField - Multiple node field with no values")
    public void buildNodeField_multipleWithNoValues() throws Exception {
        Node mockNode = new NodeBuilder()
                .withEmptyChildNode("nodePropPath")
                .build();

        IFieldDescriptor mockFieldDescriptor = new IFieldDescriptorBuilder()
                .withPath("nodePropPath")
                .isMultiple(true)
                .build();

        Field nodeField = generator.buildNodeField(mockFieldDescriptor, mockNode);

        assertThat(nodeField).isInstanceOf(MultipleField.class);

        MultipleField multipleField = (MultipleField)nodeField;
        assertThat(multipleField.getField()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("buildNodeField - Multiple node field with values")
    public void buildNodeField_multipleWithValues() throws Exception {
        new ITypeDescriptorBuilder()
                .withType("childNode1")
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withDeclaredFieldMap(
                        createDeclaredFieldMap(
                            new IFieldDescriptorBuilder().withPath("childNode1prop").isPrimary(true).build()
                        )
                ).build();

        Node mockNode = new NodeBuilder()
                .withChildNode("nodePropPath",
                        new NodeBuilder().withPrimaryNodeType("childNode1").withProperty("childNode1prop", "value1").build())
                .withChildNode("nodePropPath",
                        new NodeBuilder().withPrimaryNodeType("childNode1").withProperty("childNode1prop", "value2").build())
                .build();

        IFieldDescriptor mockFieldDescriptor = new IFieldDescriptorBuilder()
                .withPath("nodePropPath")
                .isMultiple(true)
                .build();

        Field nodeField = generator.buildNodeField(mockFieldDescriptor, mockNode);

        assertThat(nodeField).isInstanceOf(MultipleField.class);

        MultipleField multipleField = (MultipleField)nodeField;
        assertThat(multipleField.getField()).isNotNull().hasSize(2);
        assertThat(multipleField.getField()).containsExactly(
                new FieldValue(null, "value1"),
                new FieldValue(null, "value2")
        );
    }

    @Test
    @DisplayName("buildNodeField - Single node field with no values")
    public void buildNodeField_singleWithNoValue() throws Exception {
        Node mockNode = new NodeBuilder()
                .withEmptyChildNode("nodePropPath")
                .build();

        IFieldDescriptor mockFieldDescriptor = new IFieldDescriptorBuilder()
                .withPath("nodePropPath")
                .isMultiple(false)
                .build();

        Field nodeField = generator.buildNodeField(mockFieldDescriptor, mockNode);

        assertThat(nodeField).isInstanceOf(SingleField.class);

        SingleField singleField = (SingleField)nodeField;
        assertThat(singleField.getField()).isEqualTo(new FieldValue(null, ""));
    }

    @Test
    @DisplayName("buildNodeField - Single node field with values")
    public void buildNodeField_singleWithValue() throws Exception {
        new ITypeDescriptorBuilder()
                .withType("childNode1")
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withDeclaredFieldMap(
                        createDeclaredFieldMap(
                                new IFieldDescriptorBuilder()
                                        .withPath("childNode1prop")
                                        .isPrimary(true)
                                        .build()
                        )
                )
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .build();

        Node mockNode = new NodeBuilder()
                .withChildNode("nodePropPath",
                        new NodeBuilder().withPrimaryNodeType("childNode1").withProperty("childNode1prop", "value1").build())
                .build();

        IFieldDescriptor mockFieldDescriptor = new IFieldDescriptorBuilder()
                .withPath("nodePropPath")
                .isMultiple(false)
                .build();

        Field nodeField = generator.buildNodeField(mockFieldDescriptor, mockNode);

        assertThat(nodeField).isInstanceOf(SingleField.class);

        SingleField singleField = (SingleField)nodeField;
        assertThat(singleField.getField()).isEqualTo(new FieldValue(null ,"value1"));
    }

    @Test
    @DisplayName("buildDocumentDifferences - No fields on document type")
    public void buildDocumentDifferences_noFields() throws Exception {
        Node mockUnpublishedNode = new NodeBuilder().build();
        Node mockPublishedNode = new NodeBuilder().build();
        JcrDocument mockJcrDocument = new JcrDocumentBuilder()
                .withVariantNode(JcrDocument.VARIANT_UNPUBLISHED, mockUnpublishedNode)
                .withVariantNode(JcrDocument.VARIANT_PUBLISHED, mockPublishedNode)
                .build();
        List<IFieldDescriptor> fieldDescriptorList = new ArrayList<>();
        Map<String, String> captionMap = new HashMap<>();

        DocumentDifference diff = generator.buildDocumentDifferences(mockJcrDocument, fieldDescriptorList, captionMap);

        assertThat(diff).isNotNull();
        assertThat(diff.getDifferences()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("buildDocumentDifferences - With fields but no differences between versions")
    public void buildDocumentDifferences_withFieldsNoDifferences() throws Exception {
        // Fields for the main node type
        Map<String, IFieldDescriptor> fieldDescriptorMap = new HashMap<>();
        fieldDescriptorMap.put("field1",
                new IFieldDescriptorBuilder()
                    .withName("field1")
                    .withPath("field1path")
                    .isMultiple(false)
                    .withTypeDescriptor(
                            new ITypeDescriptorBuilder()
                                    .isNode(true)
                                    .build()
                    )
                    .build()
        );
        fieldDescriptorMap.put("field2",
                new IFieldDescriptorBuilder()
                        .withName("field2")
                        .withPath("field2path")
                        .isMultiple(false)
                        .withTypeDescriptor(
                                new ITypeDescriptorBuilder()
                                        .isNode(false)
                                        .build()
                        )
                        .build()
        );

        // Fields for the child node defined in field1 above
        Map<String, IFieldDescriptor> childNodeFieldDescriptorMap = new HashMap<>();
        childNodeFieldDescriptorMap.put("childField1",
                new IFieldDescriptorBuilder()
                    .withPath("childField1path")
                    .isPrimary(true)
                    .build());

        // Register the type for the child node.
        // Cannot do this above due to cyclic dependency between an IFieldDescriptor and the ITypeDescriptor
        new ITypeDescriptorBuilder()
                .withType("vs:child")
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withDeclaredFieldMap(childNodeFieldDescriptorMap)
                .build();


        Node mockUnpublishedNode = new NodeBuilder()
                .withChildNode("field1path",
                        new NodeBuilder()
                                .withPrimaryNodeType("vs:child")
                                .withProperty("childField1path", "childField1value")
                                .build()
                )
                .withProperty("field2path", "field2value")
                .build();

        Node mockPublishedNode = new NodeBuilder()
                .withChildNode("field1path",
                        new NodeBuilder()
                                .withPrimaryNodeType("vs:child")
                                .withProperty("childField1path", "childField1value")
                                .build()
                )
                .withProperty("field2path", "field2value")
                .build();

        JcrDocument mockJcrDocument = new JcrDocumentBuilder()
                .withVariantNode(JcrDocument.VARIANT_UNPUBLISHED, mockUnpublishedNode)
                .withVariantNode(JcrDocument.VARIANT_PUBLISHED, mockPublishedNode)
                .build();

        List<IFieldDescriptor> fieldDescriptorList = new ArrayList<>(fieldDescriptorMap.values());

        Map<String, String> captionMap = new HashMap<>();
        captionMap.put("field1", "field1caption");
        captionMap.put("field2", "field2caption");

        DocumentDifference diff = generator.buildDocumentDifferences(mockJcrDocument, fieldDescriptorList, captionMap);

        assertThat(diff).isNotNull();
        assertThat(diff.getDifferences()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("buildDocumentDifferences - With fields and differences between versions")
    public void buildDocumentDifferences_withFieldsAndDifferences() throws Exception {
        // Fields for the main node type
        Map<String, IFieldDescriptor> fieldDescriptorMap = new HashMap<>();
        fieldDescriptorMap.put("field1",
                new IFieldDescriptorBuilder()
                        .withName("field1")
                        .withPath("field1path")
                        .isMultiple(false)
                        .withTypeDescriptor(
                                new ITypeDescriptorBuilder()
                                        .isNode(true)
                                        .build()
                        )
                        .build()
        );
        fieldDescriptorMap.put("field2",
                new IFieldDescriptorBuilder()
                        .withName("field2")
                        .withPath("field2path")
                        .isMultiple(false)
                        .withTypeDescriptor(
                                new ITypeDescriptorBuilder()
                                        .isNode(false)
                                        .build()
                        )
                        .build()
        );

        // Fields for the child node defined in field1 above
        Map<String, IFieldDescriptor> childNodeFieldDescriptorMap = new HashMap<>();
        childNodeFieldDescriptorMap.put("childField1",
                new IFieldDescriptorBuilder()
                        .withPath("childField1path")
                        .isPrimary(true)
                        .build());

        // Register the type for the child node.
        // Cannot do this above due to cyclic dependency between an IFieldDescriptor and the ITypeDescriptor
        new ITypeDescriptorBuilder()
                .withType("vs:child")
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withDeclaredFieldMap(childNodeFieldDescriptorMap)
                .build();


        Node mockUnpublishedNode = new NodeBuilder()
                .withChildNode("field1path",
                        new NodeBuilder()
                                .withPrimaryNodeType("vs:child")
                                .withProperty("childField1path", "childField1valueDiff")
                                .build()
                )
                .withProperty("field2path", "field2valueDiff")
                .build();

        Node mockPublishedNode = new NodeBuilder()
                .withChildNode("field1path",
                        new NodeBuilder()
                                .withPrimaryNodeType("vs:child")
                                .withProperty("childField1path", "childField1value")
                                .build()
                )
                .withProperty("field2path", "field2value")
                .build();

        JcrDocument mockJcrDocument = new JcrDocumentBuilder()
                .withVariantNode(JcrDocument.VARIANT_UNPUBLISHED, mockUnpublishedNode)
                .withVariantNode(JcrDocument.VARIANT_PUBLISHED, mockPublishedNode)
                .build();

        List<IFieldDescriptor> fieldDescriptorList = new ArrayList<>(fieldDescriptorMap.values());

        Map<String, String> captionMap = new HashMap<>();
        captionMap.put("field1", "field1caption");
        captionMap.put("field2", "field2caption");

        DocumentDifference diff = generator.buildDocumentDifferences(mockJcrDocument, fieldDescriptorList, captionMap);

        assertThat(diff).isNotNull();
        assertThat(diff.getDifferences()).isNotNull().hasSize(2);

        FieldDifference expectedField1Diff = new FieldDifference();
        expectedField1Diff.setProperty("field1");
        expectedField1Diff.setCaption("field1caption");
        SingleField latest = new SingleField();
        latest.setField(new FieldValue(null, "childField1valueDiff"));
        expectedField1Diff.setLatest(latest);
        SingleField previous = new SingleField();
        previous.setField(new FieldValue(null, "childField1value"));
        expectedField1Diff.setPrevious(previous);

        Optional<FieldDifference> field1diff =
                diff.getDifferences().stream()
                        .filter(difference -> difference.getProperty().equals("field1")).findFirst();
        assertThat(field1diff.isPresent()).isTrue();
        assertThat(field1diff.get()).isEqualTo(expectedField1Diff);

        FieldDifference expectedField2Diff = new FieldDifference();
        expectedField2Diff.setProperty("field2");
        expectedField2Diff.setCaption("field2caption");
        SingleField latest2 = new SingleField();
        latest2.setField(new FieldValue(null, "field2valueDiff"));
        expectedField2Diff.setLatest(latest2);
        SingleField previous2 = new SingleField();
        previous2.setField(new FieldValue(null, "field2value"));
        expectedField2Diff.setPrevious(previous2);

        Optional<FieldDifference> field2diff =
                diff.getDifferences().stream()
                        .filter(difference -> difference.getProperty().equals("field2")).findFirst();
        assertThat(field2diff.isPresent()).isTrue();
        assertThat(field2diff.get()).isEqualTo(expectedField2Diff);
    }

    @Test
    @DisplayName("buildDocumentDifferences - With value format exception")
    public void buildDocumentDifferences_valueFormatException() throws Exception {
        // When a value format exception is thrown it should not cause the difference comparison to fail
        // Fields for the main node type
        Map<String, IFieldDescriptor> fieldDescriptorMap = new HashMap<>();
        fieldDescriptorMap.put("field1",
                new IFieldDescriptorBuilder()
                        .withName("field1")
                        .withPath("field1path")
                        .isMultiple(false)
                        .withTypeDescriptor(
                                new ITypeDescriptorBuilder()
                                        .isNode(true)
                                        .build()
                        )
                        .build()
        );
        fieldDescriptorMap.put("field2",
                new IFieldDescriptorBuilder()
                        .withName("field2")
                        .withPath("field2path")
                        .isMultiple(false)
                        .withTypeDescriptor(
                                new ITypeDescriptorBuilder()
                                        .isNode(false)
                                        .build()
                        )
                        .build()
        );

        // Fields for the child node defined in field1 above
        Map<String, IFieldDescriptor> childNodeFieldDescriptorMap = new HashMap<>();
        childNodeFieldDescriptorMap.put("childField1",
                new IFieldDescriptorBuilder()
                        .withPath("childField1path")
                        .isPrimary(true)
                        .build());

        // Register the type for the child node.
        // Cannot do this above due to cyclic dependency between an IFieldDescriptor and the ITypeDescriptor
        new ITypeDescriptorBuilder()
                .withType("vs:child")
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withDeclaredFieldMap(childNodeFieldDescriptorMap)
                .build();


        Node mockUnpublishedNode = new NodeBuilder()
                .withChildNode("field1path",
                        new NodeBuilder()
                                .withPrimaryNodeType("vs:child")
                                .withProperty("childField1path", "childField1valueDiff")
                                .build()
                )
                .withPropertyThowsValueFormatException("field2path")
                .build();

        JcrDocument mockJcrDocument = new JcrDocumentBuilder()
                .withVariantNode(JcrDocument.VARIANT_UNPUBLISHED, mockUnpublishedNode)
                .build();

        List<IFieldDescriptor> fieldDescriptorList = new ArrayList<>(fieldDescriptorMap.values());

        Map<String, String> captionMap = new HashMap<>();
        captionMap.put("field1", "field1caption");
        captionMap.put("field2", "field2caption");

        DocumentDifference diff = generator.buildDocumentDifferences(mockJcrDocument, fieldDescriptorList, captionMap);

        assertThat(diff).isNotNull();
        assertThat(diff.getDifferences()).isNotNull().hasSize(1);

        FieldDifference expectedField1Diff = new FieldDifference();
        expectedField1Diff.setProperty("field1");
        expectedField1Diff.setCaption("field1caption");
        SingleField latest = new SingleField();
        latest.setField(new FieldValue(null, "childField1valueDiff"));
        expectedField1Diff.setLatest(latest);
        SingleField previous = new SingleField();
        previous.setField(new FieldValue(null, ""));
        expectedField1Diff.setPrevious(previous);

        Optional<FieldDifference> field1diff =
                diff.getDifferences().stream()
                        .filter(difference -> difference.getProperty().equals("field1")).findFirst();
        assertThat(field1diff.isPresent()).isTrue();
        assertThat(field1diff.get()).isEqualTo(expectedField1Diff);
    }

    @Test
    @DisplayName("buildDocumentDifferences - With missing live version")
    public void buildDocumentDifferences_withMissingLiveVersion() throws Exception {
        // Fields for the main node type
        Map<String, IFieldDescriptor> fieldDescriptorMap = new HashMap<>();
        fieldDescriptorMap.put("field1",
                new IFieldDescriptorBuilder()
                        .withName("field1")
                        .withPath("field1path")
                        .isMultiple(false)
                        .withTypeDescriptor(
                                new ITypeDescriptorBuilder()
                                        .isNode(true)
                                        .build()
                        )
                        .build()
        );
        fieldDescriptorMap.put("field2",
                new IFieldDescriptorBuilder()
                        .withName("field2")
                        .withPath("field2path")
                        .isMultiple(false)
                        .withTypeDescriptor(
                                new ITypeDescriptorBuilder()
                                        .isNode(false)
                                        .build()
                        )
                        .build()
        );

        // Fields for the child node defined in field1 above
        Map<String, IFieldDescriptor> childNodeFieldDescriptorMap = new HashMap<>();
        childNodeFieldDescriptorMap.put("childField1",
                new IFieldDescriptorBuilder()
                        .withPath("childField1path")
                        .isPrimary(true)
                        .build());

        // Register the type for the child node.
        // Cannot do this above due to cyclic dependency between an IFieldDescriptor and the ITypeDescriptor
        new ITypeDescriptorBuilder()
                .withType("vs:child")
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withDeclaredFieldMap(childNodeFieldDescriptorMap)
                .build();


        Node mockUnpublishedNode = new NodeBuilder()
                .withChildNode("field1path",
                        new NodeBuilder()
                                .withPrimaryNodeType("vs:child")
                                .withProperty("childField1path", "childField1valueDiff")
                                .build()
                )
                .withProperty("field2path", "field2valueDiff")
                .build();

        JcrDocument mockJcrDocument = new JcrDocumentBuilder()
                .withVariantNode(JcrDocument.VARIANT_UNPUBLISHED, mockUnpublishedNode)
                .build();

        List<IFieldDescriptor> fieldDescriptorList = new ArrayList<>(fieldDescriptorMap.values());

        Map<String, String> captionMap = new HashMap<>();
        captionMap.put("field1", "field1caption");
        captionMap.put("field2", "field2caption");

        DocumentDifference diff = generator.buildDocumentDifferences(mockJcrDocument, fieldDescriptorList, captionMap);

        assertThat(diff).isNotNull();
        assertThat(diff.getDifferences()).isNotNull().hasSize(2);

        FieldDifference expectedField1Diff = new FieldDifference();
        expectedField1Diff.setProperty("field1");
        expectedField1Diff.setCaption("field1caption");
        SingleField latest = new SingleField();
        latest.setField(new FieldValue(null, "childField1valueDiff"));
        expectedField1Diff.setLatest(latest);
        SingleField previous = new SingleField();
        previous.setField(new FieldValue(null, ""));
        expectedField1Diff.setPrevious(previous);

        Optional<FieldDifference> field1diff =
                diff.getDifferences().stream()
                        .filter(difference -> difference.getProperty().equals("field1")).findFirst();
        assertThat(field1diff.isPresent()).isTrue();
        assertThat(field1diff.get()).isEqualTo(expectedField1Diff);

        FieldDifference expectedField2Diff = new FieldDifference();
        expectedField2Diff.setProperty("field2");
        expectedField2Diff.setCaption("field2caption");
        SingleField latest2 = new SingleField();
        latest2.setField(new FieldValue(null, "field2valueDiff"));
        expectedField2Diff.setLatest(latest2);
        SingleField previous2 = new SingleField();
        previous2.setField(new FieldValue(null, ""));
        expectedField2Diff.setPrevious(previous2);

        Optional<FieldDifference> field2diff =
                diff.getDifferences().stream()
                        .filter(difference -> difference.getProperty().equals("field2")).findFirst();
        assertThat(field2diff.isPresent()).isTrue();
        assertThat(field2diff.get()).isEqualTo(expectedField2Diff);
    }

    @Test
    @DisplayName("getTranslationDifference - Simple happy path")
    public void getTranslationDifference() throws Exception {
        // A happy path for coverage of code without branches
        Node node = new NodeBuilder()
                .withNodeId("node1")
                .inJcrSession(mockJcrSession).build();

        Node unpublishedNode = new NodeBuilder()
                .withPrimaryNodeType("variant:type").build();

        ITypeDescriptor unpublishedType = new ITypeDescriptorBuilder()
                .addToJcrTypeLocator(mockJcrTypeLocator)
                .withType("variant:type")
                .withDeclaredFieldMap(new HashMap<>()).build();

        addTypeToTemplateStore(unpublishedType, createIClusterConfig());

        new JcrDocumentBuilder()
                .withVariantNode(JcrDocument.VARIANT_UNPUBLISHED, unpublishedNode)
                .withJcrDocumentFactory(mockJcrDocumentFactory)
                .createdFromNode(node).build();

        DocumentDifference diff = generator.getTranslationDifference("node1");
        assertThat(diff).isNotNull();
        assertThat(diff.getDifferences()).isNotNull().isEmpty();
    }

    protected Value[] createValues(Value... valueArray) {
        return valueArray;
    }

    protected Value createValue(String stringValue) throws Exception {
        Value mockValue = mock(Value.class);
        when(mockValue.getString()).thenReturn(stringValue);
        return mockValue;
    }

    protected IPluginConfig createIPluginConfig(String field, String caption) {
        IPluginConfig mockPlugin = mock(IPluginConfig.class);
        lenient().when(mockPlugin.get(eq("field"))).thenReturn(field);
        lenient().when(mockPlugin.get(eq("caption"))).thenReturn(caption);
        return mockPlugin;
    }
    protected IClusterConfig createIClusterConfig(IPluginConfig... configArray) {
        List<IPluginConfig> pluginConfig = Arrays.asList(configArray);
        IClusterConfig config = mock(IClusterConfig.class);
        lenient().when(config.getPlugins()).thenReturn(pluginConfig);
        return config;
    }

    protected void addTypeToTemplateStore(ITypeDescriptor typeDescriptor, IClusterConfig... configArray) {
        List<IClusterConfig> configList = Arrays.asList(configArray);
        templateStoreMap.put(typeDescriptor, configList);
    }

    protected Map<String, IFieldDescriptor> createDeclaredFieldMap(IFieldDescriptor... fields) {
        Map<String, IFieldDescriptor> fieldMap = new HashMap<>();
        int count = 1;
        for (IFieldDescriptor field : fields) {
            String name = field.getName();
            if (null == name) {
                name = "anon" + count++;
            }
            fieldMap.put(name, field);
        }
        return fieldMap;
    }
}
