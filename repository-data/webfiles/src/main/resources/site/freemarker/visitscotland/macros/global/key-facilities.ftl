<#--  <#include "../../../vs-dotcom-ds/components/icon-list.ftl">
<#include "../../../vs-dotcom-ds/components/icon-list-item.ftl">  -->

<#macro keyFacilities facilitiesList>
    <vs-icon-list title="${label('keyFacilities', 'keyfacilitiestitle')}">
        <#list facilitiesList as facility>
            <vs-icon-list-item
                icon="${facility.id}"
                label="${facility.name}">
            </vs-icon-list-item>
        </#list>
    </vs-icon-list>
</#macro>