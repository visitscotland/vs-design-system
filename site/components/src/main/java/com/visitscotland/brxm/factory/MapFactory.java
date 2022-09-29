package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.hippobeans.Destination;
import com.visitscotland.brxm.hippobeans.MapModule;
import com.visitscotland.brxm.hippobeans.Stop;
import com.visitscotland.brxm.model.FlatLink;
import com.visitscotland.brxm.model.MapsModule;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.builder.HstQueryBuilder;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.hippoecm.hst.content.beans.query.builder.ConstraintBuilder.constraint;

@Component
public class MapFactory {

    private ImageFactory imageFactory;

    private QuoteFactory quoteEmbedder;

    public MapFactory(ImageFactory imageFactory, QuoteFactory quoteEmbedder){
        this.imageFactory = imageFactory;
        this.quoteEmbedder = quoteEmbedder;
    }

    public MapsModule getModule(HstRequest request, MapModule doc){
        MapsModule module = new MapsModule();
        List<FlatLink> featured = new ArrayList<>();

        module.setTitle(doc.getTitle());
        module.setIntroduction(doc.getCopy());
        module.setTabTitle(doc.getTabTitle());
        module.setFeauredPlaces(doc.getCategories());
        module.setHippoBean(doc);
       /* HstRequestContext requestContext = request.getRequestContext();
        HippoBean scope = requestContext.getSiteContentBaseBean();
        for (String taxonomy: doc.getKeys()) {
            HstQuery hstQuery = HstQueryBuilder.create(scope)
                    .ofTypes(Destination.class, Stop.class)
                    .where(constraint("@hippotaxonomy:keys").contains(taxonomy)).build();

            HstQueryResult result = null;
            try {
                result = hstQuery.execute();
                if (result != null) {
                    List <String> name = new ArrayList<>();
                    while (result.getHippoBeans().hasNext()) {
                        HippoBean bean = result.getHippoBeans().nextHippoBean();
                        if (bean != null) {
                            name.add(bean.getDisplayName());
                        }
                    }
                }
            } catch (QueryException e) {
                e.printStackTrace();
            }
        }*/

        return module;
    }
}
