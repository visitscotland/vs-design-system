package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.frontend.session.UserSession;

public class UserSessionFactory {
    public UserSession getUserSession() {
        return UserSession.get();
    }
}
