<#macro devEnvMenu branch author prId> 
    <div class="bg-dark">
        <div fluid="lg" class="d-flex justify-content-between container-lg py-2">
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