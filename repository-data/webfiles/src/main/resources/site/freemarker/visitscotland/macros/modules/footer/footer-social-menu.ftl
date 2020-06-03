<#include "../../../../include/imports.ftl">
<#include "../../../../vs-dotcom-ds/components/footer-social-menu.ftl">
<#include "../../../../vs-dotcom-ds/components/footer-social-item.ftl">
<#include "../../../../vs-dotcom-ds/components/list.ftl">

<#macro footerSocialMenu>
    <vs-footer-social-menu>
        <span slot="title">
            ${label("navigation", "footer.find-us-on")}
        </span>

        <vs-footer-social-item
            href="${label('social-media', 'facebook')}"
            icon="facebook"
        ></vs-footer-social-item>
        <vs-footer-social-item
            href="${label('social-media', 'twitter')}"
            icon="twitter"
        ></vs-footer-social-item>
        <vs-footer-social-item
            href="${label('social-media', 'youtube')}"
            icon="youtube"
        ></vs-footer-social-item>
        <vs-footer-social-item
            href="${label('social-media', 'instagram')}"
            icon="instagram"
        ></vs-footer-social-item>
    </vs-footer-social-menu>
</#macro>