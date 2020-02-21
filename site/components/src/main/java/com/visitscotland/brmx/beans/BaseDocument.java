package com.visitscotland.brmx.beans;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.builder.HstQueryBuilder;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

@Node(jcrType="visitscotland:basedocument")
public class BaseDocument extends HippoDocument {

    private static final String BASE_DOCUMENT = "visitscotland:basedocument";

    private static final Logger logger = LoggerFactory.getLogger(BaseDocument.class.getName());

    public static final String DOCUMENT_TYPE = "jcr:primaryType";

    public String getPrimaryType() {
        try {
            return String.valueOf(node.getProperty("jcr:primaryType"));
        } catch (RepositoryException e) {
            logger.error(String.format("jcr:primaryType has not been defined by the node %s ({%s})", getName(), getClass()));
            return null;
        }
    }

    public <T extends HippoBean> List<T> getExternalBeansByType(Class<T> type){
        String documentType = type.getAnnotation(Node.class).jcrType();
        return getSiblingDocuments(documentType, type);
    }


    public List<HippoBean> getExternalBeans(){
        return getFolderItems(BASE_DOCUMENT);
    }

    /**
     * Executes a query and log when no results are returned
     * @param documentType
     * @return
     */
    private List<HippoBean> getFolderItems(String documentType){
        List<HippoBean> nodes = new ArrayList<>();

        HstQuery query = HstQueryBuilder.create(this.getParentBean().getNode())
                .ofTypes(documentType)
                .build();

        try {
            HippoBeanIterator it = query.execute().getHippoBeans();

            while (it.hasNext()){
                nodes.add(it.next());
            }
        } catch (QueryException e) {
            e.printStackTrace();
        }

        return nodes;
    }

    public <T> T getOnlyChild(List<T> children){
        if (children.size() == 0) {
            return null;
        } else if (children.size() == 1) {
            return children.get(0);
        } else {
            logger.warn("This list in supposed to have only one child");
            //TODO: get childs
            return children.get(0);
        }
    }

    public <T> List<T> getSiblingDocuments(String documentType, Class<T> typeClass) {
        //Get the list of sibling nodes
        final NodeIterator it;
        final List<T> documents = new ArrayList<>();

        try {
            it = getNode().getParent().getParent().getNodes();
        } catch (RepositoryException e) {
            //TODO fix the error message
            logError("Error trying to connect to the repository", node, e);
            return documents;
        }

        while (it.hasNext()) {
            javax.jcr.Node jcrNode = it.nextNode();
            try {
                if (jcrNode.getNodes().getSize() > 0) {
                    String primaryType = jcrNode.getNodes().nextNode().getProperty(DOCUMENT_TYPE).getString();
                    if (documentType.equals(primaryType)) {
                        HippoBean bean = RequestContextProvider.get().getQueryManager()
                                .createQuery(jcrNode).execute().getHippoBeans().nextHippoBean();

                        Object aux = getObjectConverter().getObject(bean.getNode());
                        //The document is added if the type matches
                        //TODO we need some kind of
                        if (aux != null && aux.getClass().isAssignableFrom(typeClass)) {
                            documents.add((T) aux);
                        }
                    }
                }
            } catch (QueryException | RepositoryException | NullPointerException | ObjectBeanManagerException e) {
                logError("The following node is corrupted", node, e);
            }
        }
        return documents;
    }

    private void logError(String message, javax.jcr.Node node, Exception e){
        try {
            logger.error(message + node.getPath(), e);
        } catch (RepositoryException e1) {
            logger.error(message + ". A nested exception happened while trying to access to the node ", e1);
        }
    }
}
