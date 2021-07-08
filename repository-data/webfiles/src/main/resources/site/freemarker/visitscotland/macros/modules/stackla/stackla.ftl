<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-module-wrapper.ftl">
<#include "../../../../frontend/components/vs-stackla-wrapper.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.StacklaModule" -->

<#macro stackla module>
    <vs-module-wrapper class="theme-${themeName}">
        <template slot="vsModuleWrapperHeading">
            ${module.title}
        </template>
        <vs-stackla-wrapper>
            <template slot="stacklaIntroCopy">
                <@hst.html hippohtml=module.copy/>
            </template>
            <template slot="stacklaIntroCopyNoJs">
                <p>JavaScript needs to be enabled to see social media images for this place. You can
                turn this on in your browser settings.</p>
            </template>
            <template slot="stacklaWidget">
                <div class="stackla-widget" data-ct="" data-hash="${module.dataHash}"
                    data-id="${module.dataId}" data-title="social_vs.org_IGfeed" data-ttl="60"
                    style="width: 100%; overflow: hidden;"></div>
            </template>
        </vs-stackla>
    </vs-module-wrapper>
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
</#macro>