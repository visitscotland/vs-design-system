package com.visitscotland.brmx.rest;

import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.Language;
import com.visitscotland.brmx.utils.Properties;
import org.hippoecm.hst.jaxrs.services.AbstractResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.soap.Node;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Path("/internal/")
public class InternalResource extends AbstractResource {

    private static final Logger logger = LoggerFactory.getLogger(InternalResource.class);

    static final String NO_MATCH = "<!-- No match -->";

    //  TODO: TEST @Autowired
    private final CommonUtils utils;

    public InternalResource() {
        this(new CommonUtils());
    }

    InternalResource(CommonUtils utils) {
        this.utils = utils;
    }

    @GET
    @Path("/")
    public Response health() {
        return Response.ok().entity("status OK!").build();
    }

    @GET
    @Path("/{fragment}")
    public Response fragment(@PathParam("fragment") String fragment,
                           @QueryParam("root-path") String rootPath,
                           @QueryParam("sso") String sso,
                           @QueryParam("external") String external,
                           @QueryParam("vs-locale-ctx") String locale) {
        String url = buildUrl(external, rootPath, sso, locale);
        try {
            String body = getFragment(utils.requestUrl(url), fragment);
            if (NO_MATCH.equals(body)){
                return Response.status(Response.Status.NOT_FOUND).entity(body).build();
            } else {
                return Response.ok().entity(body).build();
            }
        } catch (Exception e) {
            logger.error("Error while requesting the data to {} ", url, e);
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