package com.visitscotland.brmx.components.navigation;

import com.visitscotland.brmx.beans.Widget;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VsHstSiteMenuItemImpl implements VsMenuItem {

    private final HstSiteMenuItem menuItem;
    private final List<HstSiteMenuItem> children;
    private final VsHstSiteMenuItemImpl parent;

    private String title;
    private Widget widget;


    public VsHstSiteMenuItemImpl(VsHstSiteMenuItemImpl parent, HstSiteMenuItem menuItem) {
        this.children = new ArrayList<>();
        this.menuItem = menuItem;
        this.parent = parent;
        if (parent != null){
            parent.addChild(this);
        }
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getName() {
        return menuItem.getName();
    }

    @Override
    public List<HstSiteMenuItem> getChildMenuItems() {
        return children;
    }

    private void addChild(HstSiteMenuItem child){
        children.add(child);
    }

    @Override
    public HstSiteMenuItem getParentItem() {
        return parent;
    }

    // Delegating methods

    @Override
    public HstSiteMenu getHstSiteMenu() {
        return menuItem.getHstSiteMenu();
    }

    @Override
    public String getParameter(String s) {
        return menuItem.getParameter(s);
    }

    @Override
    public String getLocalParameter(String s) {
        return menuItem.getLocalParameter(s);
    }

    @Override
    public Map<String, String> getParameters() {
        return menuItem.getParameters();
    }

    @Override
    public Map<String, String> getLocalParameters() {
        return menuItem.getLocalParameters();
    }

    @Override
    public HstLink getHstLink() {
        return menuItem.getHstLink();
    }

    @Override
    public String getExternalLink() {
        return menuItem.getExternalLink();
    }

    @Override
    public ResolvedSiteMapItem resolveToSiteMapItem() {
        return menuItem.resolveToSiteMapItem();
    }

    @Override
    public boolean isExpanded() {
        return menuItem.isExpanded();
    }

    @Override
    public Map<String, Object> getProperties() {
        return menuItem.getProperties();
    }

    @Override
    public boolean isRepositoryBased() {
        return menuItem.isRepositoryBased();
    }

    @Override
    public int getDepth() {
        return menuItem.getDepth();
    }

    @Override
    public boolean isSelected() {
        return menuItem.isSelected();
    }
}