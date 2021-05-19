package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.hippobeans.Stackla;
import com.visitscotland.brxm.model.StacklaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StacklaFactory {

    private static final Logger logger = LoggerFactory.getLogger(StacklaFactory.class);

    public StacklaModule getStacklaModule(Stackla document) {
        logger.info("Creating stacklaModule for {}", document.getPath());
        StacklaModule stacklaModule = new  StacklaModule();
        stacklaModule.setTitle(document.getTitle());
        stacklaModule.setCopy(document.getCopy());
        stacklaModule.setDataId(document.getStacklaId());
        stacklaModule.setDataHash(document.getStacklaHash());
        stacklaModule.setHippoBean(document);
        return stacklaModule;
    }


}
