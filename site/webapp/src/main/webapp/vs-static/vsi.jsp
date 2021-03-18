<html>
<head>
<title>VSI</title>
</head>
<body>
<%@  page import="java.net.InetAddress" %>
<% InetAddress ia = InetAddress.getLocalHost(); %>

  <br>request from: <%= request.getRemoteHost() %>
  <br>for url:      <STRONG><%= request.getServerName() %></STRONG><%= request.getRequestURI() %>
  <br>served by:    <%= ia.getHostName() %>

  <br>
    <table width="100%" border="1">
        <tr>
            <td width="10%">request method</td>
             <!-- getMethod() returns the name of the HTTP method with which this request was made, for example, GET, POST, or PUT -->
             <td><%= request.getMethod() %></td>
        </tr>
        <tr>
            <td width="10%">request scheme</td>
             <td><%= request.getScheme() %></td>
        </tr>
        <tr>
            <td>server name</td>
            <td><%= request.getServerName() %></td>
        </tr>
        <tr>
            <td>server port</td>
            <td><%= request.getServerPort() %></td>
        </tr>
        <tr>
            <td>local port</td>
            <td><%= request.getLocalPort() %></td>
        </tr>
        <tr>
            <td>remote port</td>
            <td><%= request.getRemotePort() %></td>
        </tr>
        <tr>
            <td>request URI</td>
            <!-- getRequestURI() returns the part of this request's URL -->
            <td><%= request.getRequestURI() %></td>
        </tr>
        <tr>
            <td>request URL</td>
            <!-- getRequestURI() returns the part of this request's URL -->
            <td><%= (request.getRequestURL()).toString() %></td>
        </tr>
        <tr>
            <td>query string</td>
            <td><%= request.getQueryString() %></td>
        </tr>
        <tr></tr>
        <tr></tr>
        <tr></tr>
        <tr>
            <td>context path</td>
            <td><%= request.getContextPath() %></td>
        </tr>
        <tr>
            <td>context path</td>
            <td>using "c:out value="${pageContext.request.contextPath}"": <c:out value="${pageContext.request.contextPath}" /><br>
                using "\$\{pageContext.request.contextPath\}":                   ${pageContext.request.contextPath}</td>
        </tr>
        <tr>
            <td>servlet path</td>
            <td><%= request.getServletPath() %></td>
        </tr>
        <tr>
            <td>path info</td>
            <td><%= request.getPathInfo() %></td>
        </tr>

        <tr></tr>
        <tr></tr>
        <tr></tr>

        <%
         /*This method returns an enumeration of all the header names this
          request contains.*/
         java.util.Enumeration names = request.getHeaderNames();
         while (names.hasMoreElements()) {
         String hname = (String)names.nextElement();
        %>
        <tr>
            <td valign="top"> <%= hname %> </td>
                <!-- This method returns the value of the specified request header as a String. -->
                <td><%= request.getHeader(hname) %></td>
        </tr>
   <%
        }
   %>
   </table>
</body>
</html>