package com.visitscotland.brmx.beans.mapping.megalinks;

import com.visitscotland.brmx.beans.Megalinks;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.Module;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

import java.util.List;

public class LinksModule<L extends FlatLink> extends Module {

    private String title;
    private HippoHtml introduction;
    private List<L> links;
    private FlatLink cta;
    private Megalinks megalinkItem;
    private String style;
    private String alignment;

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

    public List<L> getLinks() {
        return links;
    }

    public void setLinks(List<L> links) {
        this.links = links;
    }

    public FlatLink getCta() {
        return cta;
    }

    public void setCta(FlatLink cta) {
        this.cta = cta;
    }

    public Megalinks getMegalinkItem() {
        return megalinkItem;
    }

    public void setMegalinkItem(Megalinks megalinkItem) {
        this.megalinkItem = megalinkItem;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }
}
