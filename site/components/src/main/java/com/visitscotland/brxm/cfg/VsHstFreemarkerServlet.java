package com.visitscotland.brxm.cfg;

import com.visitscotland.brxm.services.ResourceBundleService;
import freemarker.template.TemplateModelException;
import org.hippoecm.hst.servlet.HstFreemarkerServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class VsHstFreemarkerServlet extends HstFreemarkerServlet {

    private static final Logger LOG = LoggerFactory.getLogger(VsHstFreemarkerServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            getConfiguration().setSharedVariable("ResourceBundle", VsComponentManager.get(ResourceBundleService.class));
        } catch (TemplateModelException e){
            LOG.error("Unable to set shared variables.", e);
        }
    }
}
