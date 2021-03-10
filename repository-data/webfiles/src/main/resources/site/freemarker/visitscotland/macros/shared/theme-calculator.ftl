
<#function themeCalculator module colourScheme>
    <#if !module.theme?has_content>
        <#assign themeName = "light">
    <#elseif module.theme="theme1">
        <#assign themeName = colourScheme[0]>
    <#elseif module.theme="theme2">
        <#assign themeName = colourScheme[1]>
    <#elseif module.theme="theme3">
        <#assign themeName = colourScheme[2]>
    </#if>
    <#return themeName>
</#function>
