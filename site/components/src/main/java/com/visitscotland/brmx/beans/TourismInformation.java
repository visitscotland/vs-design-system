package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import com.visitscotland.brmx.beans.ICentre;
import com.visitscotland.brmx.beans.IKnow;

@HippoEssentialsGenerated(internalName = "visitscotland:TourismInformation")
@Node(jcrType = "visitscotland:TourismInformation")
public class TourismInformation extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:ICentre")
    public ICentre getICentre() {
        return getBean("visitscotland:ICentre", ICentre.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:IKnow")
    public IKnow getIKnow() {
        return getBean("visitscotland:IKnow", IKnow.class);
    }
}
