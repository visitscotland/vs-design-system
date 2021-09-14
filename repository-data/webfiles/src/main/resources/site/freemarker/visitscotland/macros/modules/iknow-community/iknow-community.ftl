<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-container.ftl">
<#include "../../../../frontend/components/vs-col.ftl">
<#include "../../../../frontend/components/vs-row.ftl">
<#include "../../../../frontend/components/vs-img.ftl">
<#include "../../../../frontend/components/vs-tag.ftl">
<#include "../../../../frontend/components/vs-link.ftl">
<#include "../../../../frontend/components/vs-list.ftl">
<#include "../../../../frontend/components/vs-module-wrapper.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.IKnowCommunityModule" -->

<#macro iknowCommunity module>
    <vs-module-wrapper theme="<#if themeName?has_content>${themeName}<#else>light</#if>">
        <template slot="vsModuleWrapperHeading">
            ${module.title}
        </template>
        <vs-container>
            <vs-row>
                <vs-col
                    cols="10"
                    offset="1"
                    md="8"
                    offset-md="2"
                    lg="6"
                    offset-lg="3"
                    class="text-center"
                >
                    <vs-img 
                        src="<@hst.webfile path='assets/images/iknow-community-logo.svg'/>" 
                        width="190px">
                    </vs-img>
                </vs-col>
            </vs-row>
            <vs-row>
                <vs-col
                    cols="12"
                    md="10"
                    offset-md="1"
                    lg="6"
                    offset-lg="0"
                    xl="5"
                    offset-xl="1"
                    class="text-left py-10"
                >
                    <@hst.html hippohtml=module.copy/>

                    <vs-link
                        class="mt-4"
                        href="${module.link.link}"
                        <#if module.link.type != "internal">type="${module.link.type}"</#if>
                    >
                        ${module.link.label}
                    </vs-link>

                    <vs-list class="mt-10" unstyled inline>
                        <#list module.tags as tag>
                            <li>
                                <vs-tag href="${tag.link}">
                                    ${tag.label}
                                </vs-tag>
                            </li>
                        </#list>
                    </vs-list>
                </vs-col>
                <vs-col
                    cols="10"
                    offset="1"
                    md="6"
                    offset-md="3"
                    lg="6"
                    offset-lg="0"
                    xl="4"
                    offset-xl="1"
                    class="pt-lg-10"
                >
                    <vs-img 
                        src="<@hst.webfile path='assets/images/illustrations/iknow-coo-puffin.svg'/>">
                    </vs-img>
                </vs-col>
            </vs-row>
        </vs-container>
    </vs-module-wrapper>
</#macro>