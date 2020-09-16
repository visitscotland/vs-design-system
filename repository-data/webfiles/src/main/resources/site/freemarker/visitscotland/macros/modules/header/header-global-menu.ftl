<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-global-menu.ftl">

<#macro headerGlobalMenu>
    <vs-global-menu
        dropdown-label='<@fmt.message key="global-menu.our-websites" />'
        active-site="https://www.visitscotland.com/"
    ></vs-global-menu>
</#macro>