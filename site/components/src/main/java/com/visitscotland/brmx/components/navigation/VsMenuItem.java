package com.visitscotland.brmx.components.navigation;


import com.visitscotland.brmx.beans.Widget;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;

public interface VsMenuItem extends HstSiteMenuItem {

    Widget getWidget();

    String getTitle();

}
