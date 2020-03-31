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