<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-global-menu.ftl">
<#include "../../../../frontend/components/vs-global-menu-language.ftl">
<#include "../../../../frontend/components/vs-global-menu-language-item.ftl">

<#-- @ftlvariable name="language" type="com.visitscotland.brmx.beans.mapping.LocalizedURL"-->

<#assign currentLocale=hstRequest.requestContext.resolvedMount.mount.locale>

<#macro headerGlobalMenu>
    <vs-global-menu
            dropdown-label="${label('navigation.static', 'uninav.our-sites')}"
            active-site="https://www.visitscotland.com/"
    >
    <template slot="third-menu-item">
        <vs-global-menu-language current-locale="${currentLocale}" language-label="${label('navigation.static', 'universal.language')}">
                <#list localizedURLs as language>
                    <vs-global-menu-language-item
                            key="${language.language}"
                            language-link="${language.url}"
                            language-name="${language.displayName}<#-- (${language.isExists()?c}) -->"
                    >
                    </vs-global-menu-language-item>
                </#list>
        </vs-global-menu-language>
    </template>
</vs-global-menu>
</#macro>