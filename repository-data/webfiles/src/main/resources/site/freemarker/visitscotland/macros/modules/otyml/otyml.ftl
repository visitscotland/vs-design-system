<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-module-wrapper.ftl">
<#include "../carousel/carousel.ftl">

<#macro otyml item>
    <vs-module-wrapper>
        <template slot="moduleWrapperHeading">
            ${item.title}
        </template>

        <template slot="moduleWrapperIntro">
            <@hst.html hippohtml=item.introduction/>
        </template>

        <@carousel item />
    </vs-module-wrapper>
</#macro>