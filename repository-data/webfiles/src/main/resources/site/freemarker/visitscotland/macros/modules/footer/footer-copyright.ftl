<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-footer-copyright.ftl">

<#macro footerCopyright>
    <vs-footer-copyright href="<@hst.link siteMapItemRefId='root'/>" logo-alt-text="${label('essentials.global', 'footer.logo-alt-text')}">
        <span slot="copyright">
            ${label('essentials.global', 'footer.text')}
        </span>
    </vs-footer-copyright>
</#macro>