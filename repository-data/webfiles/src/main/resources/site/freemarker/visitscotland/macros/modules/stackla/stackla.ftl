<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-module-wrapper.ftl">
<#include "../../../../frontend/components/vs-container.ftl">
<#include "../../../../frontend/components/vs-row.ftl">
<#include "../../../../frontend/components/vs-col.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.StacklaModule" -->

<#macro stackla module>
    <vs-module-wrapper class="theme-${themeName}">
        <template slot="vsModuleWrapperHeading">
            ${module.title}
        </template>
        <template slot="vsModuleWrapperIntro">
            <@hst.html hippohtml=module.copy/>
        </template>

        <vs-container>
            <vs-row>
                <vs-col>
                    <div>
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
                                t.src = '//assetscdn.stackla.com/media/js/widget/fluid-embed.js';
                                t.id = id;
                                (d.getElementsByTagName('head')[0] || d.getElementsByTagName('body')[0]).appendChild(t);
                            }(document, 'stackla-widget-js'));
                        </script>

                        <!-- Stackla Widget Embed Code (end) -->
                    </div>
                </vs-col>
            </vs-row>
        </vs-container>
    </vs-module-wrapper>
</#macro>