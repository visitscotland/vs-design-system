<#macro devEnvMenu branch author prId> 
    <div class="bg-dark">
        <div class="d-flex flex-column flex-lg-row justify-content-lg-between container-lg py-2">
            <span class="text-warning"><strong>Branch:</strong> ${branch}</span>
            <span class="text-warning"><strong>Author:</strong> ${author}</span>
            <#if ciPrID??>
                <span class="text-warning">(<strong>PR:</strong> ${prId})</span>
            </#if>
            <#if vsProperties??>
                hello
            </#if>
        </div>
    </div>
</#macro>