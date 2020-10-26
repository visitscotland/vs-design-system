<#include "../../../include/imports.ftl">
<#include "../global/cms-errors.ftl">
<#include "../modules/megalinks/megalinks.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brmx.beans.Megalinks" -->
<#-- @ftlvariable name="image" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->

<#-- @ftlvariable name="heroImage" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="hero" type="com.visitscotland.brmx.beans.Image" -->

<#--TODO Control abput colours, change style="background-color:${style}  -->
<#macro moduleBuilder module theme>
    <div class="has-edit-button" style="background-color:${theme}">
   
        <#if module.getType() == "MultiImageLinksModule">
            <@megalinks item=module type=module.getType() />

        <#--Macro for single image-->
        <#elseif module.getType()== "SingleImageLinksModule">
            <@megalinks item=module type=module.getType() />

        <#--Macro for list-->
        <#--  <#elseif module.getType()== "ListLinksModule">
            <@megalinks item=module type=module.getType() />  -->

        <#elseif module.getType()== "ICentreModule">
            <#if module.image.cmsImage??>
                <#assign image>
                    <@hst.link hippobean=module.image.cmsImage.original/>
                </#assign>
            <#else>
                <#assign image = module.image.externalImage!'' />
            </#if>

            <#if module.quoteImage??>
                <#assign imageQuote>
                    <@hst.link hippobean=module.quoteImage.cmsImage.original/>
                </#assign>
            </#if>

            <vs-col>
            <#--TODO for links the image does not have caption-->
                <@imageWithCaption imageSrc=image imageDetails=module.image variant="fullwidth"/>
            </vs-col>

            <vs-row>
                <vs-col cols="12" md="10" lg="10" xl="10" offset-lg="1">
                    <@imageWithCaption imageSrc=imageQuote imageDetails=module.quoteImage variant="fullwidth"/>
                    "<@hst.html hippohtml=module.quote/>"
                    </br>
                    <#if module.quoteLink??>
                        <vs-button animate=false background="purple" variant="primary" href="${module.quoteLink.link}" size="md">
                            ${module.quoteLink.label}
                        </vs-button>

                    </#if>
                    <vs-heading level="6">${module.quoteAuthorName}</vs-heading>
                    ${module.quoteAuthorTitle}
                </vs-col>


                <vs-col cols="12" md="10" lg="10" xl="10" offset-lg="1">
                    <@hst.html hippohtml=module.description/>
                </vs-col>
                <vs-col cols="4" lg="4" offset-lg="1">
                    <#list module.iCentreList as iCentre>
                        <vs-link href="${iCentre.link}">${iCentre.label}</vs-link>
                        </br>
                    </#list>
                </vs-col>
            </vs-row>

        <#elseif module.getType()== "IKnowModule">
            <@hst.manageContent hippobean=module.tourismInformation />
                        <vs-row>
                            <vs-col cols="12" md="10" lg="10" xl="10" offset-lg="1">
                                <@hst.html hippohtml=module.description/>
                            </vs-col>
                            <vs-col cols="4" lg="4" offset-lg="1">
                                <vs-link href="${module.link.link}">iKnow partners in this area</vs-link>
                                </br>
                            </vs-col>
                        </vs-row>

        </#if>
    </div>
</#macro>
