package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import com.visitscotland.brmx.beans.OTYML;

@HippoEssentialsGenerated(internalName = "visitscotland:General")
@Node(jcrType = "visitscotland:General")
public class General extends Page {
    @HippoEssentialsGenerated(internalName = "visitscotland:theme")
    public String getTheme() {
        return getSingleProperty("visitscotland:theme");
    }

    @Override
    public String[] getChildJcrTypes() {
        return new String[] { "visitscotland:Megalinks",
                "visitscotland:TourismInformation" };
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:otherThings")
    public OTYML getOtherThings() {
        return getBean("visitscotland:otherThings", OTYML.class);
    }
}
