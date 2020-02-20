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