
<#-- frontend/src/utils/data-layer-templates.js -->
<#function pageViewDLEvent >
    <#assign url = hstRequest.request.pathInfo >
    <#assign event = "{
        'url' : '${url}',
        'language': '${language}',
        'user_country_setting': 'User Country Test',"
    >
    <#if location??>
        <#if location.isRegion() >
            <#assign event = event + "'provider_region': '${location.name}'">
        <#else >
            <#assign event = event + "'provider_city': '${location.name}'">
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