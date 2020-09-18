<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-footer-copyright.ftl">

<#macro footerCopyright>
    <vs-footer-copyright href="<@hst.link siteMapItemRefId='root'/>" logo-alt-text="${label('navigation.footer', 'footer.logo-alt-text')}">
        <span slot="copyright">
            ${label('navigation.footer', 'footer.copyright-text')}
        </span>
    </vs-footer-copyright>
</#macro>