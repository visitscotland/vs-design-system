<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-article.ftl">
<#include "../../global/image-with-caption.ftl">
<#include "article-section.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.ArticleModule" -->
<#-- @ftlvariable name="section" type="com.visitscotland.brxm.model.ArticleModuleSection" -->

<#macro article module>
    <@hst.manageContent hippobean=module.hippoBean />
    <#if module.image??>
        <#if module.image.cmsImage??>
            <#assign image>
                <@hst.link hippobean=module.image.cmsImage.original/>
            </#assign>
        <#else>
            <#assign image = module.image.externalImage!'' />
        </#if>
    <#else>
        <#assign image = "" />
    </#if>

    <vs-article isStandardPage>
        <#if image?? && image?has_content>
            <template slot="vsArticleImg">
                <@imageWithCaption imageSrc=image imageDetails=module.image variant="fullwidth"/>
            </template>
        </#if>

        <template slot="vsArticleTitle">
            <#if module.anchor?has_content>
                <span id="${module.anchor}">${module.title}</span>
            <#else>
                ${module.title}
            </#if>
        </template>

        <template slot="vsArticleIntro">
            <@hst.html hippohtml=module.introduction/>
        </template>

        <#assign i = 0/>
        <#assign alignSidebar = ""/>

        <#list module.sections as section>
            <#if section.quote?? || section.image??>
                <#assign i++ />
                <#if i % 2 != 0 >
                    <#assign alignSidebar = "left" />
                <#else>
                    <#assign alignSidebar = "right" />
                </#if>
            <#else>
                <#assign alignSidebar = "left" />
            </#if>

            <@articleSection section=section alignSidebar=alignSidebar />
         </#list>        
    </vs-article>
</#macro>