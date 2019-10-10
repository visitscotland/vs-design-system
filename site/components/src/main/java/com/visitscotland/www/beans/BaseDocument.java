package com.visitscotland.www.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.builder.HstQueryBuilder;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

@Node(jcrType="visitscotland:basedocument")
public class BaseDocument extends HippoDocument {

    private static final String BASE_DOCUMENT = "visitscotland:basedocument";

    private static final Logger logger = LoggerFactory.getLogger(BaseDocument.class.getName());

    public String getPrimaryType() {
        try {
            return String.valueOf(node.getProperty("jcr:primaryType"));
        } catch (RepositoryException e) {
            logger.error(String.format("jcr:primaryType has not been defined by the node %s ({%s})", getName(), getClass()));
            return null;
        }
    }

    public List<HippoBean> getExternalBeansByType(String documentType){
        return getFolderItems(documentType);
    }

    public List<HippoBean> getExternalBeans(){
        return getFolderItems(BASE_DOCUMENT);
    }

    /**
     * Executes a query and log when no results are returned
     * @param session
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

}
