package com.visitscotland.brxm.translation.report;

public class TranslationReportException extends RuntimeException{

        public TranslationReportException() {

        }

        public TranslationReportException(String message) {
            super(message);
        }

        public TranslationReportException(String message, Throwable ex) {
            super(message, ex);
        }

        public TranslationReportException(Throwable ex) {
            super(ex);
        }
}
