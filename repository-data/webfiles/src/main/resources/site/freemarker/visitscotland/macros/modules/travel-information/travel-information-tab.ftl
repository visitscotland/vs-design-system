<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.TravelInformationTabModule" -->

<#macro travelInformationTab module>
    <h2>${module.title}</h2>
    <ul>
        <#list module.travelInformationTransportRows as row>
            <li>
                <h3>${row.transport.label} (${row.transport.key})</h3>
                <@hst.html hippohtml=row.copy/>
            </li>
        </#list>
    </ul>
</#macro>