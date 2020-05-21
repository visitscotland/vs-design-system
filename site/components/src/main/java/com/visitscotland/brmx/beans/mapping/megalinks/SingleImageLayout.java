package com.visitscotland.brmx.beans.mapping.megalinks;

import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class SingleImageLayout extends AbstractLayout {

    private String innerTitle;
    private HippoHtml innerIntroduction;
    private FlatImage image;
    private boolean fullWidth;

    public String getInnerTitle() {
        return innerTitle;
    }

    public void setInnerTitle(String innerTitle) {
        this.innerTitle = innerTitle;
    }

    public HippoHtml getInnerIntroduction() {
        return innerIntroduction;
    }

    public void setInnerIntroduction(HippoHtml innerIntroduction) {
        this.innerIntroduction = innerIntroduction;
    }

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
}
