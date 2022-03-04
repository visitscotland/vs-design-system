<#include "../../../frontend/components/vs-container.ftl">
<#include "../../../frontend/components/vs-alert.ftl">
<#include "../../../frontend/components/vs-list.ftl">

<#macro previewWarning editMode module hidden=false >
<#--    editMode ${editMode?c} errors? ${module.errorMessages?has_content?c}-->
    <#if editMode && module.errorMessages?has_content >
        <vs-container
            class="py-4"
        >
            <vs-alert>
                <div>
                    <#if hidden>
                        <p>There is a major issue with the document <b>${module.hippoBean.displayName}</b> and It was removed from the page:</p>
                    <#else>
                        <p>The following issues have been detected if this module:</p>
                    </#if>
                    <vs-list>
                        <#list module.errorMessages as error>
                            <li>${error}</li>
                        </#list>
                    </vs-list>
                </div>
            </vs-alert>
        </vs-container>
    </#if>
</#macro>