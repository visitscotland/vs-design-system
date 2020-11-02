package com.visitscotland.brmx.translation;

import org.hippoecm.frontend.session.UserSession;

import javax.jcr.Session;

public class SessionFactory {
    public Session getJcrSession() {
        return UserSession.get().getJcrSession();
    }
    public UserSession getUserSession() { return UserSession.get(); }
}
