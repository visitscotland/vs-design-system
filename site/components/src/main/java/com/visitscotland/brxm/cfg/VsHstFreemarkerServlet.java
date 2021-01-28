package com.visitscotland.brxm.cfg;

import com.visitscotland.utils.info.About;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import org.hippoecm.hst.servlet.HstFreemarkerServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * This Piece of code Extracted from the dot-org project and Enhanced for the dot-com needs (Dependency Injection Coming soon)
 *
 * {@code com.visitscotland.org.util.VSHstFreemarkerServlet.java}
 */
public class VsHstFreemarkerServlet extends HstFreemarkerServlet {

    private static final Logger logger = LoggerFactory.getLogger(VsHstFreemarkerServlet.class);

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        includeVersionNumber();
    }

    private void includeVersionNumber(){
        Configuration freemarkerConfig = super.getConfiguration();
        try {
            //Sets the version number as a Freemarker shared variable so it can be inserted to all pages.
            if (About.getVersion().equals("Unknown")){
                freemarkerConfig.setSharedVariable("version", getClass().getPackage().getImplementationVersion());
            } else {
                freemarkerConfig.setSharedVariable("version", About.getVersion() + " (" + About.getBuildNumber() + ")");
            }
        } catch (TemplateModelException e) {
            logger.error("Unable to set shared variables.", e);
        }
    }
}
