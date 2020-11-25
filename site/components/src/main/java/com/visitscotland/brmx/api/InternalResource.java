package com.visitscotland.brmx.api;

import com.visitscotland.brmx.utils.CommonUtils;
import org.hippoecm.hst.jaxrs.services.AbstractResource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Path("/internal/")
public class InternalResource extends AbstractResource {

    private final CommonUtils utils;

    public InternalResource(){
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

            HttpURLConnection con = requestPage(external, rootPath, sso, locale);
            return prepareResponse(con, tag);
        } catch (Exception e) {
            //TODO Log properly
            e.printStackTrace();
            return null;
        }
    }

    private HttpURLConnection requestPage(String external,
                                          String rootPath,
                                          String sso,
                                          String locale) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("external", external);
        parameters.put("root-path", rootPath);
        parameters.put("sso", sso);
        parameters.put("vs-locale-ctx", locale);


        HttpURLConnection con = (HttpURLConnection) new URL("http://localhost:8080/site/internal" + buildQueryString(parameters)).openConnection();
        con.setDoInput(true);

        con.setRequestProperty("Content-Type", "text/html");
        con.setRequestProperty("Method", "GET");

        return con;
    }


    /**
     * This method should be converted into a builder pattern and the parameter escaped.
     * @param parameters
     * @return
     */
    private String buildQueryString(Map<String, String> parameters){
        //TODO Sonar Recomendations
        if (parameters.size() > 0){
            String query = "?";
            for (String key: parameters.keySet()){
                if (parameters.get(key) == null){
                    continue;
                }
                if (query.length() > 1){
                    query += "&";
                }
                //TODO escape parameters
                query += key + "=" + parameters.get(key);
            }
            return query;
        }
        return "";
    }


//    /**
//     * Calculates the domain
//     * @param con
//     * @param tag
//     * @param domain
//     * @return
//     */
//    private String getPath(HttpServletRequest request){
//        String path;
//        if (request.getParameterValues("root-path") != null){
//            path = request.getParameter("root-path");
//            if (!path.endsWith("/")){
//                path += "/";
//            }
//        } else if (request.getParameterValues("external") != null){
//
//            path = request.getScheme() + "://" +
//                   request.getServerName();
//            if (request.getServerPort() != 80) {
//               path += ":" + request.getServerPort();
//            }
//            path += "/";
//
//        } else {
//            path = "/";
//        }
//
//        return path;
//    }


    private Response prepareResponse(String url, String tag) {
        try {
            return Response.ok().entity(getFragment(utils.requestUrl(url), tag)).build();
        } catch (IOException e){
            return Response.serverError().entity("Error while handling the request. Please contact Helpdesk.").build();
        }
    }

    private Response prepareResponse(HttpURLConnection con, String tag) {
        try {
            StringBuilder sb = new StringBuilder();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
//                utils.requestUrl()
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                return Response.ok().entity(getFragment(sb.toString(), tag)).build();
            } else {
                return Response.serverError().entity(String.format("The application responded with an error code %s: %s", con.getResponseCode(), con.getResponseMessage())).build();
            }
        } catch (IOException e){
            return Response.serverError().entity("Error while handling the request. Please contact Helpdesk.").build();
        }
    }

    private String getFragment(String content, String tag){
        final String open = "<internal-" + tag + ">";
        final String close = "</internal-" + tag + ">";

        try {
            return content.substring(content.indexOf(open)+ open.length(),
                content.indexOf(close));
        } catch (Exception e){
            //TODO WARN no match
            return "<!-- No match -->";
        }
    }

}
