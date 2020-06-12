<#include "../../../../include/imports.ftl">
<#include "../../../../vs-dotcom-ds/components/footer-copyright.ftl">

<#macro footerCopyright>
    <vs-footer-copyright href="/" logo-alt-text="${label('essentials.global', 'footer.logo-alt-text')}">
        <span slot="copyright">
            ${label('essentials.global', 'footer.text')}
        </span>
    </vs-footer-copyright>
</#macro>