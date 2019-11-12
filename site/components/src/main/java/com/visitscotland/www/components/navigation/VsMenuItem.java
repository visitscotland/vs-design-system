package com.visitscotland.www.components.navigation;


import com.visitscotland.www.beans.Widget;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;

public interface VsMenuItem extends HstSiteMenuItem {

    Widget getWidget();

    String getTitle();

}
