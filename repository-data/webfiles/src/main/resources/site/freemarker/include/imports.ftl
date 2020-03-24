<#assign hst=JspTaglibs["http://www.hippoecm.org/jsp/hst/core"] >
<#assign fmt=JspTaglibs ["http://java.sun.com/jsp/jstl/fmt"] >

<@hst.defineObjects />

<#assign locale = hstRequest.requestContext.resolvedMount.mount.locale?replace("_","-")?lower_case>
<#include "./helpers.ftl">