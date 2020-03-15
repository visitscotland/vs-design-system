<#--
    Functions
-->

<#--  More reliable method for including labels from resource bundles  -->
<#--  e.g. ${label("essentials.global", "footer.signup")} -->
<#function label bundle key>
    <@hst.setBundle basename="${bundle}"/>
    <@fmt.message var="message" key="${key}" />
    <#return message>
</#function>

<#function productSearch locale productType lat lon proximity>
    <#--TODO: proximity as optional-->
    <#return ProductSearchBuilder.newInstance().createProductSearch(productType).proximity(proximity).coordinates(lat, lon).build()>
</#function>

<#--TODO: polimorphism-->
<#--<#function productSearch locale productType location >-->
    <#--<#return ProductSearchBuilder.newInstance().createProductSearch(productType).locale(locale).location(location).build()>-->
<#--</#function>-->