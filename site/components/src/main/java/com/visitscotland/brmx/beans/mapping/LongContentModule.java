package com.visitscotland.brmx.beans.mapping;

import com.visitscotland.brmx.beans.Image;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

import java.util.List;

public class LongContentModule extends Module {
    private Image image;
    private String title;
    private HippoHtml introduction;
    private List<FlatLongContentSection> sections;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HippoHtml getIntroduction() {
        return introduction;
    }

    public void setIntroduction(HippoHtml introduction) {
        this.introduction = introduction;
    }

    public List<FlatLongContentSection> getSections() {
        return sections;
    }

    public void setSections(List<FlatLongContentSection> sections) {
        this.sections = sections;
    }
}
