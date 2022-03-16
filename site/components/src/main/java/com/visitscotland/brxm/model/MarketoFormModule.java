package com.visitscotland.brxm.model;

import com.visitscotland.brxm.hippobeans.MarketoForm;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class MarketoFormModule extends Module<MarketoForm> {

    private String title;
    private String configuration;
    private String noJavaScriptMessage;
    private HippoHtml copy;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getNoJavaScriptMessage() {
        return noJavaScriptMessage;
    }

    public void setNoJavaScriptMessage(String noJavaScriptMessage) {
        this.noJavaScriptMessage = noJavaScriptMessage;
    }

    public HippoHtml getCopy() {
        return copy;
    }

    public void setCopy(HippoHtml copy) {
        this.copy = copy;
    }
}
