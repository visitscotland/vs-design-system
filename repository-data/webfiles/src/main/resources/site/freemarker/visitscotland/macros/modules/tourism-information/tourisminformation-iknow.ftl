<#include "../../../../include/imports.ftl">

<#macro iknow module>
    <@hst.manageContent hippobean=module.tourismInformation />
        ${module.title}
    <vs-row>
        <vs-col cols="12" md="10" lg="10" xl="10" offset-lg="1">
            <@hst.html hippohtml=module.description/>
        </vs-col>
        <vs-col cols="4" lg="4" offset-lg="1">
            <vs-link href="${module.link.link}">iKnow partners in this area</vs-link>
            </br>
        </vs-col>
    </vs-row>

</#macro>