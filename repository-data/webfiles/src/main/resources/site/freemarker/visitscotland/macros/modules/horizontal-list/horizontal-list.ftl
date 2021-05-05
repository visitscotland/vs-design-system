<#include "../../../../include/imports.ftl">
<#include "../carousel/carousel.ftl">

<#macro horizontalList item>
    <template slot="vsModuleWrapperHeading">
        ${item.title}
    </template>

    <template slot="vsModuleWrapperIntro">
        <@hst.html hippohtml=item.introduction/>
    </template>

    <@carousel item />
</#macro>