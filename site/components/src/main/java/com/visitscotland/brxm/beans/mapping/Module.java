package com.visitscotland.brxm.beans.mapping;

import org.hippoecm.hst.content.beans.standard.HippoBean;

public class Module {

    private HippoBean hippoBean;

    private String anchor;

    public String getType(){
        return getClass().getSimpleName();
    }

    public HippoBean getHippoBean() {
        return hippoBean;
    }

    public void setHippoBean(HippoBean hippoBean) {
        this.hippoBean = hippoBean;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }
}
