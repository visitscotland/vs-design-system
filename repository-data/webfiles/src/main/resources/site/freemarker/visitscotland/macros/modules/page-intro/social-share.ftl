
<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-social-share.ftl">
<#include "../../../../frontend/components/vs-social-share-item.ftl">

<#macro socialShare>
    <vs-social-share 
        page-url="<@hst.link hippobean=document canonical=true fullyQualified=true/>" 
        page-title="${document.title}"
    >
        <template slot="shareHeading">
            ${label('social.share', 'share.on')}
        </template>
        
        <vs-social-share-item
            name="facebook"
            link-text="${label('social.share', 'facebook.link.text')}"
        ></vs-social-share-item>
        <vs-social-share-item
            name="pinterest"
            link-text="${label('social.share', 'pinterest.link.text')}"
        ></vs-social-share-item>
        <vs-social-share-item
            name="whatsapp"
            link-text="${label('social.share', 'whatsapp.link.text')}"
        ></vs-social-share-item>
        <vs-social-share-item
            name="twitter"
            link-text="${label('social.share', 'twitter.link.text')}"
        ></vs-social-share-item>
        <vs-social-share-item
            name="email"
            link-text="${label('social.share', 'email.link.text')}"
        ></vs-social-share-item>
        <vs-social-share-item
            name="link"
            link-text="${label('social.share', 'copy.link.text')}"
        ></vs-social-share-item>
    </vs-social-share>
</#macro>