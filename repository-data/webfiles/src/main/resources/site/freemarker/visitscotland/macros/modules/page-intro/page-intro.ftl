
<#include "../../../../include/imports.ftl">

<#include "./summary-box/summary-box.ftl">
<#include "../../../macros/global/cms-errors.ftl">
<#include "../../../../frontend/components/vs-hero.ftl">
<#include "../../../../frontend/components/vs-page-intro.ftl">
<#include "../../../../frontend/components/vs-hero.ftl">
<#include "../../../../frontend/components/vs-container.ftl">
<#include "../../../../frontend/components/vs-row.ftl">
<#include "../../../../frontend/components/vs-col.ftl">
<#include "../../../../frontend/components/vs-rich-text-wrapper.ftl">
<#include "../../../../frontend/components/vs-img.ftl">
<#include "../../../../frontend/components/vs-button.ftl">
<#include "../../../../frontend/components/vs-link.ftl">
<#include "../../../../frontend/components/vs-heading.ftl">
<#include "../../../../frontend/components/vs-social-share.ftl">
<#include "../../../../frontend/components/vs-description-list.ftl">
<#include "../../../../frontend/components/vs-description-list-item.ftl">
<#include "../../../../frontend/components/vs-image-with-caption.ftl">
<#include "../../../../frontend/components/vs-image-location-map.ftl">
<#include "../../shared/theme-calculator.ftl">

<#macro pageIntro content heroDetails firstStop="" lastStop="">
    <#assign themeName = themeCalculator(introTheme)>

    <#if content.heroImage?has_content>
        <@hst.link var="hero" hippobean=content.heroImage.original/>
    </#if>
    
<div class="has-edit-button">
    <vs-page-intro background="${themeName}" <#if heroDetails?has_content>hero-intro</#if> <#if content.days?has_content>is-itinerary</#if>>
        <#if heroDetails?has_content>
            <vs-hero
                slot="vsIntroHero"
                alt-text="${heroDetails.altText!''}"
                credit="${heroDetails.credit!''}"
                caption="${heroDetails.description!''}"
                image-src="${hero}"
                latitude="${(heroCoordinates.latitude)!''}"
                longitude="${(heroCoordinates.longitude)!''}"
            >
                <vs-img
                    src="${hero}"
                    alt="${heroDetails.altText!''}"
                > </vs-img>
            </vs-hero>
        </#if>

        <template slot="vsIntroBreadcrumb">
            <@hst.include ref="breadcrumb"/>
        </template>

        <template slot="vsIntroHeading">
            ${document.title}
        </template>

        <template slot="vsIntroContent">
            <@hst.html hippohtml=document.introduction/>
        </template>

        <#if content.days?has_content>
            <#if firstStop?has_content && lastStop?has_content>
                <template slot="vsIntroStartFinish">
                    <dt class="list-inline-item">${label("itinerary", "start-finish")}</dt>
                    <dd class="list-inline-item">${firstStop} / ${lastStop}</dd>
                </template>
            </#if>
        
            <template slot="VsIntroSummaryBox">
                <@summaryBox content.days />
            </template>
        </#if>

        <#if content.areas?has_content>
            <vs-container slot="VsIntroLower">
                <vs-row>
                    <vs-col cols="12" lg="11" offset-lg="1">
                        <vs-description-list class="mb-6">
                            <vs-description-list-item title>
                                ${label("itinerary", "highlights")}
                            </vs-description-list-item>
                            <#list document.highlights as highlight>
                                <vs-description-list-item>
                                    ${highlight}${"\n"}
                                </vs-description-list-item>
                            </#list>
                        </vs-description-list>
                        <vs-description-list class="mb-8">
                            <vs-description-list-item title>
                                ${label("itinerary", "areas-covered")}
                            </vs-description-list-item>
                            <#list content.areas as area>
                                <vs-description-list-item>
                                    ${label("areas", "${area}")}${"\n"}
                                </vs-description-list-item>
                            </#list>
                        </vs-description-list>
                    </vs-col>
                </vs-row>
            </vs-container>
        </#if>
    </vs-page-intro>
</div>
</#macro>