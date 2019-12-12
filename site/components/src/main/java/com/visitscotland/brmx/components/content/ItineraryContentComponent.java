package com.visitscotland.brmx.components.content;



import com.visitscotland.brmx.beans.DMSLink;
import com.visitscotland.brmx.beans.Day;
import com.visitscotland.brmx.beans.Itinerary;
import com.visitscotland.brmx.beans.Stop;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import org.onehippo.forge.selection.hst.contentbean.ValueList;
import org.onehippo.forge.selection.hst.util.SelectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;


public class ItineraryContentComponent extends EssentialsContentComponent {

    private static final Logger LOG = LoggerFactory.getLogger(ItineraryContentComponent.class);

    public final String PRODUCTS_MAP = "productsMap";
    public final String DOCUMENT_HERO = "documentHero";
    public final String FIRST_STOP_LOCATION = "firstStopLocation";
    public final String LAST_STOP_LOCATION = "lastStopLocation";



    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addProducts(request, (Itinerary) request.getAttribute("document"));

    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    private void addProducts(HstRequest request, Itinerary itinerary) {

        Map<String ,JSONObject> products =  new LinkedHashMap<>();

        String firstStopLocation= itinerary.getStart();
        String lastStopLocation= "";

        for (Day day: itinerary.getDays()) {
            for (Stop stop : day.getStops()) {
                    HippoBean item = stop.getStopItem();
                    try {
                    if (item instanceof DMSLink) {
                        String id = ((DMSLink) item).getProduct();
                        //TODO Calculate environment
                            URL url = new URL("https://staging.visitscotland.com/data/product-search/map?prod_id=" + id+ "&locale="+request.getLocale().getLanguage());
                            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                            JSONObject json = new JSONObject(readAll(br));
                            JSONArray data = (JSONArray) json.get("data");

                     if(firstStopLocation.isEmpty()){
                         firstStopLocation= data.getJSONObject(0).getString("locationName");
                        }

                    lastStopLocation= data.getJSONObject(0).getString("locationName");

                    products.put(id, data.getJSONObject(0));

                    }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }


            request.setAttribute(FIRST_STOP_LOCATION, firstStopLocation);
            request.setAttribute(LAST_STOP_LOCATION, !itinerary.getFinish().isEmpty()? itinerary.getFinish():lastStopLocation);

            request.setAttribute(PRODUCTS_MAP, products);
        }
    }
}