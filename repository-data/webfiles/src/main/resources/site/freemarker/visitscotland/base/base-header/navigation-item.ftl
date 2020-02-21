<#--<#include "./navigation-item.ftl">-->

<#macro navItem menuItem>

    <#if menuItem.hstLink??>
        <#assign itemHref><@hst.link link=menuItem.hstLink/></#assign>
    <#elseif menuItem.externalLink??>
        <#assign itemHref>${menuItem.externalLink?replace("\"", "")}</#assign>
    <#else>
        <#assign itemHref></#assign>
    </#if>


    <vs-site-nav-list-item
            href="${itemHref}"
            tracking-id="dummy-tracking-id"
            key="${menuItem.name}"
    >

        ${menuItem.title?html}

        <#if menuItem.childMenuItems??>
          <template #subnav >
            <#list menuItem.childMenuItems as subItem>
                <@navItem menuItem=subItem/>
            </#list>
          </template>
        </#if>
    </vs-site-nav-list-item>


</#macro>