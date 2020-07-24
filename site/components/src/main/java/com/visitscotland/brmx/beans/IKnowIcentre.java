package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import com.visitscotland.brmx.beans.ICentre;

@HippoEssentialsGenerated(internalName = "visitscotland:iKnowIcentre")
@Node(jcrType = "visitscotland:iKnowIcentre")
public class IKnowIcentre extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:ICentre")
    public ICentre getICentre() {
        return getBean("visitscotland:ICentre", ICentre.class);
    }
}
