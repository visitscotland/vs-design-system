<#include "../../../../frontend/components/vs-article-section.ftl">
<#include "article-sidebar.ftl">

<#macro articleSection section alignSidebar>
    <vs-article-section sidebar-align="${alignSidebar}" isStandardPage>
        <template slot="articleSidebar">
            <@articleSidebar section=section alignSidebar=alignSidebar />
        </template>

        <@hst.html hippohtml=section.copy/>
    </vs-article-section>
</#macro>