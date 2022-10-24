<#include "../../../../include/imports.ftl">
<#include "../../global/preview-warning.ftl">
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

        ${module.title}

        <@hst.html hippohtml=module.introduction/>
        CONTROLs: ${module.mapControls}
        
        <#--  ${module.geoJson?replace("'", "&#39;")}  -->

        <vs-main-map-wrapper
            category-heading="${module.tabTitle}"
            :filters='${filters}'
            :places-data='${safeJson}'
            map-id="vs-map"
        >
            <template slot="closeSidePanelText">
                Close search and filter panel
            </template>
            <template slot="openSidePanelText">
                Open search and filter panel
            </template>
            <template slot="backBtnText">
                Go back one step
            </template>
            <template slot="backBtnText">
                Go back one step
            </template>
            <template slot="resetSidePanelText">
                Reset filters
            </template>
        </vs-main-map-wrapper>
</#macro>