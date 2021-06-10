package com.visitscotland.brxm.utils;

import com.visitscotland.utils.Contract;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

/**
 * Wrapper for HippoHtml. Allows for a default value to be provided for a rich text field
 */
public class HippoHtmlWrapper extends HippoHtml {

    private final HippoHtml base;
    private final String defaultHtml;

    public HippoHtmlWrapper(HippoHtml base, String defaultHtml) {
        this.defaultHtml = defaultHtml;
        this.base = base;
    }

    public HippoHtmlWrapper(String defaultHtml) {
        this(new HippoHtml(), defaultHtml);
    }

    @Override
    public String getContent() {
        return base.getContent() == null || Contract.isEmpty(base.getContent()) ? defaultHtml : base.getContent();
    }

}
