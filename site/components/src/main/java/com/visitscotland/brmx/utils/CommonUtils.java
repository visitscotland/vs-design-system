package com.visitscotland.brmx.utils;

public class CommonUtils {

    //TODO use utils library instead.
    public static final boolean isEmpty(String value){
        return value == null || value.trim().length() == 0;
    }

    public static final String contentIssue (String message, Object... parameters){
        return String.format("- [CONTENT] - " + message, parameters);
    }
}
