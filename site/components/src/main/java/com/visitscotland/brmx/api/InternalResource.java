package com.visitscotland.brmx.api;

import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.Language;
import com.visitscotland.brmx.utils.Properties;
import org.hippoecm.hst.jaxrs.services.AbstractResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Path("/internal/")
public class InternalResource extends AbstractResource {

    private static final Logger logger = LoggerFactory.getLogger(InternalResource.class);

    private static final String NO_MATCH = "<!-- No match -->";

    private final CommonUtils utils;

    public InternalResource() {
        utils = new CommonUtils();
    }

    @GET
    @Path("/")
    public Response health() {
        return Response.ok().entity("status OK!").build();
    }

    @GET
    @Path("/{tag}")
    public Response header(@Context HttpServletRequest request,
                           @PathParam("tag") String tag,
                           @QueryParam("root-path") String rootPath,
                           @QueryParam("sso") String sso,
                           @QueryParam("vs-locale-ctx") String locale) {
        // It is not possible to difference between a non send parameter and a parameter without value.
        // This is the reason why the parameter is extracted from the request instead of using injection.
        String external = request.getParameter("external");

        try {
            String url = buildUrl(external, rootPath, sso, locale);
            return Response.ok().entity(getFragment(utils.requestUrl(url), tag)).build();
        } catch (Exception e) {
            logger.error("Error while ");
            return Response.serverError().entity("Error while handling the request. Please contact Helpdesk at " + Properties.HELPDESK).build();
        }
    }

    private String buildUrl(String external,
                            String rootPath,
                            String sso,
                            String locale) {
        Map<String, String> parameters = new HashMap<>();
        String languageSubsite = "";
        parameters.put("external", external);
        parameters.put("root-path", rootPath);
        parameters.put("sso", sso);

        if (locale != null) {
            languageSubsite = "/" + Language.getLanguageForLocale(Locale.forLanguageTag(locale)).getCMSPathVariable();
        }

        return Properties.LOCALHOST + languageSubsite + "/internal" +
                utils.buildQueryString(parameters, StandardCharsets.UTF_8.name());
    }

    private String getFragment(String content, String fragment) {
        final String open = "<internal-" + fragment + ">";
        final String close = "</internal-" + fragment + ">";

        try {
            return content.substring(content.indexOf(open) + open.length(),
                    content.indexOf(close));
        } catch (Exception e) {
            logger.warn("No coincidence has been found for the fragment {}", fragment);
            return NO_MATCH;
        }
    }
}