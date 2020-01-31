package visitscotland.components;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.Plugin;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.session.UserSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.onehippo.forge.selection.frontend.model.ListItem;
import org.onehippo.forge.selection.frontend.model.ValueList;
import org.onehippo.forge.selection.frontend.provider.IValueListProvider;
import org.onehippo.forge.selection.frontend.utils.JcrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitscotland.model.ValueListLocation;

import javax.jcr.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Locale;


public class DmsLocationListProvider extends Plugin implements IValueListProvider {


    /** @deprecated */
    @Deprecated
    private static final String CONFIG_SOURCE = "source";
    private static final Logger log = LoggerFactory.getLogger(DmsLocationListProvider.class);

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
        // TODO: use getLocationsByLevel("DISTRICT", "DESTINATION")
        ValueListLocation valueList = new ValueListLocation();
        try {
            URL url = new URL("https://test1.visitscotland.com/data/location/list?level=District&level=Destination");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            String jsonText = readAll(br);
            JSONObject json = new JSONObject(jsonText);

            JSONArray c = json.getJSONArray("data");

            for (int i = 0 ; i < c.length(); i++) {
                JSONObject obj = c.getJSONObject(i);
                ListItem list = new ListItem(obj.getString("name") ,obj.getString("name"));

                valueList.add(list);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return valueList;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public List<String> getValueListNames() {
            return JcrUtils.getValueListNames(this.obtainSession());
    }

    protected Session obtainSession() {
        UserSession userSession = (UserSession)org.apache.wicket.Session.get();
        return userSession.getJcrSession();
    }
}



