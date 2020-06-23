package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.repository.api.WorkflowException;

public class TranslationException extends Exception {

    public TranslationException() {
    }

    public TranslationException(String s) {
        super(s);
    }

    public TranslationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TranslationException(Throwable throwable) {
        super(throwable);
    }
}
