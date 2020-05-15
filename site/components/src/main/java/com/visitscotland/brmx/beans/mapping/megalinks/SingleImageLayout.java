package com.visitscotland.brmx.beans.mapping.megalinks;

import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class SingleImageLayout extends AbstractLayout {

    private FlatImage image;
    private boolean fullWidth;
    private FlatLink cta;

    public FlatImage getImage() {
        return image;
    }

    public void setImage(FlatImage image) {
        this.image = image;
    }

    public boolean isFullWidth() {
        return fullWidth;
    }

    public void setFullWidth(boolean fullWidth) {
        this.fullWidth = fullWidth;
    }

    public FlatLink getCta() {
        return cta;
    }

    public void setCta(FlatLink cta) {
        this.cta = cta;
    }
}
