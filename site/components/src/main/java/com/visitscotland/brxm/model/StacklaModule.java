package com.visitscotland.brxm.model;

import com.visitscotland.brxm.hippobeans.Stackla;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class StacklaModule extends Module<Stackla> {

    private String title;
    private HippoHtml copy;
    private String dataId;
    private String dataHash;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCopy(HippoHtml copy) {
        this.copy = copy;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public void setDataHash(String dataHash) {
        this.dataHash = dataHash;
    }

    public String getTitle() {
        return title;
    }

    public HippoHtml getCopy() {
        return copy;
    }

    public String getDataId() {
        return dataId;
    }

    public String getDataHash() {
        return dataHash;
    }
}
