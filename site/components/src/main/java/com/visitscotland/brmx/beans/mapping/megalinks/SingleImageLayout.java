package com.visitscotland.brmx.beans.mapping.megalinks;

import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class SingleImageLayout extends AbstractLayout {

    private String sectionTitle;
    private HippoHtml sectionIntroduction;
    private FlatImage image;
    private boolean fullWidth;

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public HippoHtml getSectionIntroduction() {
        return sectionIntroduction;
    }

    public void setSectionIntroduction(HippoHtml sectionIntroduction) {
        this.sectionIntroduction = sectionIntroduction;
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
