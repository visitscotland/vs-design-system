package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import java.util.List;

/** 
 * TODO: Beanwriter: Failed to create getter for node type: hippo:compound
 */
@HippoEssentialsGenerated(internalName = Day.JCR_TYPE)
@Node(jcrType = Day.JCR_TYPE)
public class Day extends BaseDocument {
    public static final String JCR_TYPE = "visitscotland:Day";

    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:introduction")
    public HippoHtml getIntroduction() {
        return getHippoHtml("visitscotland:introduction");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:stops", allowModifications = false)
    public List<Stop> getStops() {
        return getLinkedBeans("visitscotland:stops", Stop.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:transports")
    public String[] getTransports() {
        return getMultipleProperty("visitscotland:transports");
    }
}
