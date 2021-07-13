<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-module-wrapper.ftl">
<#include "../../../../frontend/components/vs-stackla-wrapper.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.StacklaModule" -->

<#macro stackla module>
    <@hst.headContribution category="htmlBodyEnd">

        <script type="text/javascript">
            (function (d, id) {
                var t, el = d.scripts[d.scripts.length - 1].previousElementSibling;
                if (el) el.dataset.initTimestamp = (new Date()).getTime();
                if (d.getElementById(id)) return;
                t = d.createElement('script');
                t.src = '//assetscdn.stackla.com/media/js/widget/fluid-embed.js';
                t.id = id;
                (d.getElementsByTagName('head')[0] || d.getElementsByTagName('body')[0]).appendChild(t);
            }(document, 'stackla-widget-js'));
        </script>

    </@hst.headContribution>

    <vs-module-wrapper class="theme-${themeName}">
        <template slot="vsModuleWrapperHeading">
            ${module.title}
        </template>
        <vs-stackla-wrapper>
            <template slot="stacklaIntroCopy">
                <@hst.html hippohtml=module.copy/>
            </template>
            <template slot="stacklaIntroCopyNoJs">
                <@hst.html hippohtml=module.noJsMessage/>
            </template>
            <template slot="stacklaIntroCopyNoCookies">
                <@hst.html hippohtml=module.noCookiesMessage/>
            </template>
            <template slot="stacklaWidget">
                <div class="stackla-widget" data-ct="" data-hash="${module.dataHash}"
                    data-id="${module.dataId}" data-title="social_vs.org_IGfeed" data-ttl="60"
                    style="width: 100%; overflow: hidden;"></div>
            </template>
        </vs-stackla>
    </vs-module-wrapper>
</#macro>