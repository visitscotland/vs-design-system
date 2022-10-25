<#include "../../../../include/imports.ftl">
<#include "../../global/preview-warning.ftl">
<#include "../../../../frontend/components/vs-module-wrapper.ftl">
<#include "../../../../frontend/components/vs-main-map-wrapper.ftl">


<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.MapsModule" -->
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->

<#macro map module>
    <@hst.manageContent hippobean=module.hippoBean />
    <@previewWarning editMode module module.errorMessages />

    <#assign safeJson>
        ${module.geoJson.features?replace("'", "&#39;")}
    </#assign>

    <#assign filters>
        <#--  ${module.mapControls.map}  -->
        [
            {
                "id": "cities",
                "label": "Cities"
            },
            {
                "id": "towns",
                "label": "Towns"
            },
            {
                "id": "islands",
                "label": "Islands"
            },
            {
                "id": "regions",
                "label": "Regions"
            },
            {
                "id": "featured",
                "label": "Featured"
            }
        ]
    </#assign>
    
    <#if module.title??>
        <#assign mainHeadingExists>
            true
        </#assign>
    <#else>
        <#assign mainHeadingExists>
            false
        </#assign>
    </#if>

    ${module.filters} </br>

    <vs-module-wrapper>
        <template slot="vsModuleWrapperHeading">
            ${module.title}
        </template>

        <template slot="vsModuleWrapperIntro">
            <@hst.html hippohtml=module.introduction/>
        </template>

        <vs-main-map-wrapper
            :main-heading-exists="${mainHeadingExists}"
            category-heading="${module.tabTitle}"
            :filters='${filters}'
            :places-data='${safeJson}'
            map-id="vs-map"
        >

            <template slot="closeSidePanelText">
                <span class="sr-only">
                    ${label('map', 'map.close-panel')}
                </span>
            </template>
            <template slot="openSidePanelText">
                <span class="sr-only">
                    ${label('map', 'map.open-panel')}
                </span>
            </template>
            <template slot="backBtnText">
                ${label('map', 'map.step-back')}
            </template>
            <template slot="resetSidePanelText">
                ${label('map', 'map.reset-filters')}
            </template>
        </vs-main-map-wrapper>
    </vs-module-wrapper>
</#macro>