package com.visitscotland.brmx.beans.mapping;

import org.hippoecm.hst.content.beans.standard.HippoBean;

public class Module {

    private HippoBean hippoBean;

    public String getType(){
        return getClass().getSimpleName();
    }

    public HippoBean getHippoBean() {
        return hippoBean;
    }

    public void setHippoBean(HippoBean hippoBean) {
        this.hippoBean = hippoBean;
    }
}
