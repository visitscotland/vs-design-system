
<#-- frontend/src/utils/data-layer-templates.js -->
<#function pageViewDLEvent document>
    <#assign url = hstRequest.request.pathInfo >
    <#assign event = "{
        'url' : '${url}',
        'site_language': '${language}',
        'content_language' : '${document.locale.language}',"
    >
    <#if location??>
        <#if location.isRegion() >
            <#assign event = event + "'content_region': '${location.name}',">
        <#else >
            <#assign event = event + "'content_city': '${location.name}',">
        </#if>
    </#if>
    <#list url?split("/") as x>
        <#if x?index gt 0>
            <#assign event = event + "'page_category_${x?index}':  '${x}',">
        </#if>
    </#list>
    <#assign event = event + "}">

    <#return event>
</#function>

</script>