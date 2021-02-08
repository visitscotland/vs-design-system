package com.visitscotland.brxm.beans.mapping;

import org.hippoecm.hst.content.beans.standard.HippoBean;

import java.util.ArrayList;
import java.util.List;

public class Module {

    private HippoBean hippoBean;

    private String anchor;

    private List<String> errorMessages;

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

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void addErrorMessage(String message){
        if (errorMessages == null){
            errorMessages = new ArrayList<>();
        }
        errorMessages.add(message);
    }
}
