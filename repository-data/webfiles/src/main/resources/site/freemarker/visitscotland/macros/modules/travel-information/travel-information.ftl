<#include "travel-information-tab.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.TravelInformationModule" -->

<#macro travelInformation module>
    <h1>${module.title}</h1>
    <@hst.html hippohtml=module.copy/>
    <@travelInformationTab module.gettingTo />
    <@travelInformationTab module.gettingAround />
</#macro>