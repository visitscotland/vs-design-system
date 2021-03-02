package com.visitscotland.brxm.components.content.factory;

import com.visitscotland.brxm.beans.LongCopy;
import com.visitscotland.brxm.beans.mapping.LongCopyModule;
import org.hippoecm.hst.core.component.HstRequest;
import org.springframework.stereotype.Component;

@Component
public class LongCopyFactory {

    public LongCopyModule getModule(LongCopy doc){
        LongCopyModule module = new LongCopyModule();
        module.setCopy(doc.getCopy());
        return module;
    }
}
