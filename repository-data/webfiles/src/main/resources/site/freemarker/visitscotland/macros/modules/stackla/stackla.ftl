<#include "../../../../include/imports.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.StacklaModule" -->

<#macro stackla module>
    <h1>${module.title}</h1>
    <p><@hst.html hippohtml=module.copy/> <br /> </p>
    Hash = ${module.dataHash} <br />
    Id = ${module.dataId} <br />
    noCookieMessage = ${module.noCookiesMessage} <br />
    noCookiesLinkLabel = ${module.noCookiesLinkLabel} <br />
    noJsMessage = ${module.noJsMessage}

    <!-- Stackla Widget Embed Code (start) -->
    <div class="stackla-widget" data-ct="" data-hash="${module.dataHash}"
         data-id="${module.dataId}" data-title="social_vs.org_IGfeed" data-ttl="60"
         style="width: 100%; overflow: hidden;"></div>

    <script type="text/javascript">
        (function (d, id) {
            var t, el = d.scripts[d.scripts.length - 1].previousElementSibling;
            if (el) el.dataset.initTimestamp = (new Date()).getTime();
            if (d.getElementById(id)) return;
            t = d.createElement('script');
            t.src = 'https://assetscdn.stackla.com/media/js/widget/fluid-embed.js';
            t.id = id;
            (d.getElementsByTagName('head')[0] || d.getElementsByTagName('body')[0]).appendChild(t);
        }(document, 'stackla-widget-js'));
    </script>

    <!-- Stackla Widget Embed Code (end) -->
</#macro>