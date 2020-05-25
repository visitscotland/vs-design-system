package com.visitscotland.brmx.beans.mapping.megalinks;

import com.visitscotland.brmx.beans.MegaLinks;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

import java.util.List;

public abstract class AbstractLayout<L extends FlatLink> {

    private String title;
    private HippoHtml introduction;
    private List<L> links;
    private FlatLink cta;
    //reference for edit mode
    private MegaLinks megaLinkItem;

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

    public MegaLinks getMegaLinkItem() {
        return megaLinkItem;
    }

    public void setMegaLinkItem(MegaLinks megaLinkItem) {
        this.megaLinkItem = megaLinkItem;
    }
}
