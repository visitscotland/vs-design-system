<#--
    Functions
-->

<#--  More reliable method for including labels from resource bundles  -->
<#--  e.g. ${label("essentials.global", "footer.signup")} -->
<#function legacyLabel bundle key>
    <@hst.setBundle basename="${bundle}"/>
    <@fmt.message var="message" key="${key}" />
    <#return message>
</#function>

<#-- @ftlvariable name="ResourceBundle" type="com.visitscotland.brmx.services.ResourceBundleService" -->

<#--  More reliable method for including labels from resource bundles  -->
<#--  e.g. ${label("essentials.global", "footer.signup")} -->
<#function label bundle key>
    <#return ResourceBundle.getResourceBundle(bundle, key, locale, false)>
</#function>

<#--  More reliable method for including labels from resource bundles  -->
<#--  e.g. ${label("essentials.global", "footer.signup")} -->
<#function optionalLabel bundle key>
    <#return ResourceBundle.getResourceBundle(bundle, key, locale, true)>
</#function>

<#function productSearch locale productType lat lon proximity>
    <#--TODO: proximity as optional-->
    <#return ProductSearchBuilder.newInstance().productTypes(productType).proximity(proximity).coordinates(lat, lon).build()>
</#function>

<#--TODO: polimorphism-->
<#--<#function productSearch locale productType location >-->
    <#--<#return ProductSearchBuilder.newInstance().productType(productType).locale(locale).location(location).build()>-->
<#--</#function>-->