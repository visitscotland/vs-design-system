package com.visitscotland.brxm.model;

import com.visitscotland.brxm.hippobeans.Stackla;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class StacklaModule extends Module<Stackla> {

    public StacklaModule(String title, HippoHtml copy, String dataId, String dataHash) {
        this.title = title;
        this.copy = copy;
        this.dataId = dataId;
        this.dataHash = dataHash;
    }

    private final String title;
    private final HippoHtml copy;
    private final String dataId;
    private final String dataHash;

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
