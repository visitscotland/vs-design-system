package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.repository.HippoStdNodeType;
import org.hippoecm.repository.translation.HippoTranslatedNode;

import javax.jcr.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides helper methods for working with the hippo documents.
 * Each document is represented by a handle Node with multiple Variant documents,
 * this class encapsulates that functionality.
 */
public class JcrDocument {
    public static final String HIPPO_HANDLE = "hippo:handle";
    public static final String HIPPOSTD_STATE = "hippostd:state";

    public static final String VARIANT_PUBLISHED = "published";
    public static final String VARIANT_UNPUBLISHED = "unpublished";
    public static final String VARIANT_DRAFT = "draft";
    private Node handle;
    // Do not access directly, will be lazy loaded
    private Map<String, Node> variantMap;

    /**
     * Will create an instance from the hippostd:handle instance or one of the document variants.
     * @param handle
     */
    public JcrDocument(Node handle) throws RepositoryException {
        if (handle.isNodeType(HIPPO_HANDLE)) {
            this.handle = handle;
        } else {
            Node parent = handle.getParent();
            if (null != parent && parent.isNodeType(HIPPO_HANDLE)) {
                this.handle = parent;
            } else {
                throw new IllegalArgumentException("the Node supplied must be a handle or a document variant");
            }
        }
    }

    /**
     * Gets the variants of the document, or if it has not been initialised will populate the variants.
     *
     * @return A Map of the variant hippostd:state to the variant Node
     */
    public Map<String, Node> getVariants() throws RepositoryException {
        if (null == variantMap) {
            variantMap = new HashMap<>();
            NodeIterator varIterator = handle.getNodes();
            while (varIterator.hasNext()) {
                Node variant = varIterator.nextNode();
                try {
                    Property state = variant.getProperty(HIPPOSTD_STATE);
                    variantMap.put(state.getString(), variant);
                } catch(PathNotFoundException ex) {
                    // If the Node does not have the hippostd:state ignore it
                }
            }
        }
        return variantMap;
    }

    /**
     * Returns the variant with the matching state, or returns null if not present.
     * @param state the vairiant state to match, draft, unpublished, or published
     * @return the variant Node matching the given state
     * @throws RepositoryException
     */
    public Node getVariantNode(String state) throws RepositoryException {
        return getVariants().get(state);
    }

    /**
     * Will look return true if the unpublished variant of a document matches one of the jcr types.
     *
     * @param jcrType
     * @return
     * @throws RepositoryException
     */
    public boolean isNodeType(String... jcrType) throws RepositoryException {
        Node unpublished = getVariants().get(VARIANT_UNPUBLISHED);
        if (null != unpublished) {
            for (String type : jcrType) {
                if (unpublished.isNodeType(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the first Node that is a parent of this document.
     * @return
     * @throws RepositoryException
     */
    public Node getContainingFolder() throws RepositoryException {
        Node parent = handle.getParent();
        while(!parent.isNodeType(HippoStdNodeType.NT_FOLDER)) {
            parent = parent.getParent();
            if (null == parent) {
                throw new IllegalStateException("No folder found for Node");
            }
        }
        return parent;
    }

    /**
     * Gets the handle Node for the document.
     * @return hippostd:handle Node
     */
    public Node getHandle() {
        return handle;
    }

    /**
     * Converts the document to a HippoBean
     * @return
     */
    public HippoBean asHippoBean() throws ObjectBeanManagerException {
        HippoBean bean = (HippoBean) RequestContextProvider.get().getContentBeansTool()
                .getObjectConverter().getObject(handle);
        return bean;
    }

    public boolean hasTranslation(ILocaleProvider.HippoLocale targetlocale) throws RepositoryException {
        HippoTranslatedNode translatedNode = new HippoTranslatedNode(getVariantNode(VARIANT_UNPUBLISHED));
        return translatedNode.hasTranslation(targetlocale.getName());
    }
}
