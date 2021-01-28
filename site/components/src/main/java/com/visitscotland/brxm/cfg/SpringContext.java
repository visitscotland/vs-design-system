package com.visitscotland.brxm.cfg;

import com.visitscotland.brxm.components.content.InternalParameterProcessor;
import com.visitscotland.brxm.components.content.PageTemplateBuilder;
import com.visitscotland.brxm.components.content.factory.LinkModulesFactory;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.utils.HippoUtilsService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {

    public static AnnotationConfigApplicationContext context;

    private static AnnotationConfigApplicationContext getContext(){
        if (context == null) {
            context = new AnnotationConfigApplicationContext(Config.class);
        }

        return context;
    }

    public static <T> T getBean(String name){
        return (T) getContext().getBean(name);
    }

    public static PageTemplateBuilder getPageTemplateBuilder(){
        return (PageTemplateBuilder) getContext().getBean("pageTemplateBuilder");
    }

    public static ResourceBundleService getResourceBundleService(){
        return (ResourceBundleService) getContext().getBean("resourceBundleService");
    }

    public static LinkService getLinkService(){
        return (LinkService) getContext().getBean("linkService");
    }

    public static LocationLoader getLocationLoader(){
        return (LocationLoader) getContext().getBean("locationLoader");
    }

    public static LinkModulesFactory getLinkModulesFactory(){
        return (LinkModulesFactory) getContext().getBean("linkModulesFactory");
    }

    public static HippoUtilsService getHippoUtilsService() {
        return (HippoUtilsService) getContext().getBean("hippoUtilsService");
    }

    public static InternalParameterProcessor getInternalParameterProcessor() {
        return (InternalParameterProcessor) getContext().getBean("internalParameterProcessor");
    }

    public static DMSDataService getDMSDataService() {
        return (DMSDataService) getContext().getBean("dMSDataService");
    }
}
