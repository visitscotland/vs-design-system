package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.frontend.session.UserSession;

import javax.jcr.Session;

public class SessionFactory {
    public Session getJcrSession() {
        return UserSession.get().getJcrSession();
    }
}
