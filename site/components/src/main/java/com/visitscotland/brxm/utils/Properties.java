package com.visitscotland.brxm.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

//TODO Externalize properties
@Component
public class Properties {

    //TODO Calculate local environment
    //TODO USe in the globalNavigation
    private String localhost = "http://localhost:8080/site";

    //TODO private
    public static final String INSTAGRAM_API = "https://www.instagram.com/p/";

    private String helpdeskEmail = "helpdesk@visitscotland.com";

    //TODO Calculate environment
    private String dmsHost = "http://172.28.81.65:8089";

    private Charset dmsEncoding = StandardCharsets.UTF_8;
    private String dmsToken = "tokenID";
    private Integer dmsTimeout = 2000;
    private Integer dmsTries = 3;
    private Integer dmsWaitTime = 60_000;

    public String getLocalhost() {
        return localhost;
    }

    public String getInstagramApi() {
        return INSTAGRAM_API;
    }

    public String getHelpdeskEmail() {
        return helpdeskEmail;
    }

    public String getDmsHost() {
        return dmsHost;
    }

    public Charset getDmsEncoding() {
        return dmsEncoding;
    }

    public String getDmsToken() {
        return dmsToken;
    }

    public Integer getDmsTimeout() {
        return dmsTimeout;
    }

    public Integer getDmsTries() {
        return dmsTries;
    }

    public Integer getDmsWaitTime() {
        return dmsWaitTime;
    }
}
