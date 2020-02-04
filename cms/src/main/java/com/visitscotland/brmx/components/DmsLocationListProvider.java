package com.visitscotland.brmx.components;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.visitscotland.brmx.beans.dms.LocationObject;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.Plugin;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.session.UserSession;
import org.onehippo.forge.selection.frontend.model.ListItem;
import org.onehippo.forge.selection.frontend.model.ValueList;
import org.onehippo.forge.selection.frontend.provider.IValueListProvider;
import org.onehippo.forge.selection.frontend.utils.JcrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.visitscotland.brmx.utils.*;
import com.visitscotland.brmx.model.ValueListLocation;

import javax.jcr.Session;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Locale;


public class DmsLocationListProvider extends Plugin implements IValueListProvider {


    /** @deprecated */
    @Deprecated
    private static final String CONFIG_SOURCE = "source";
    private static final Logger log = LoggerFactory.getLogger(DmsLocationListProvider.class);
    public static final String PRODUCT_TYPE = "dms.product.type";

    public DmsLocationListProvider(IPluginContext context, IPluginConfig config) {
        super(context, config);
        String name = config.getString("valuelist.provider", "service.dmsvaluelist.default");
        context.registerService(this, name);
        if (log.isDebugEnabled()) {
            log.debug(this.getClass().getName() + " registered under " + name);
        }
    }

    public ValueList getValueList(IPluginConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' may not be null");
        } else {
            return this.getValueList(config.getString("source"));
        }
    }

     public ValueList getValueList(String name) {
        return this.getValueList(name, (Locale)null);
        }

    public ValueList getValueList(String name, Locale locale) {
        List<LocationObject> locations = LocationLoader.getLocationsByLevel("DISTRICT", "DESTINATION");
        ValueListLocation valueList = new ValueListLocation();

        for (LocationObject location: locations) {
            ListItem list = new ListItem(location.getName(),location.getName());
            valueList.add(list);
        }
        return valueList;
    }

    public List<String> getValueListNames() {
            return JcrUtils.getValueListNames(this.obtainSession());
    }

    protected Session obtainSession() {
        UserSession userSession = (UserSession)org.apache.wicket.Session.get();
        return userSession.getJcrSession();
    }
}



