package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.frontend.translation.ILocaleProvider;

public class ChangeSetFactory {
    protected ChangeSet createChangeSet(ILocaleProvider.HippoLocale targetLocale) {
        return new ChangeSet(targetLocale);
    }
}
