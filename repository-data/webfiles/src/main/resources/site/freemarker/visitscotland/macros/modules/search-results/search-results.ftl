<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-container.ftl">
<#include "../../../../frontend/components/vs-row.ftl">
<#include "../../../../frontend/components/vs-col.ftl">

<#macro searchResults>
     <vs-container>
        <vs-row>
            <vs-col cols="12">
                <div id="cludo-search-results">
                    <div class="cludo-r">
                        <div class="cludo-c-3">
                            <div class="search-filters" role="navigation"></div>
                        </div>
                        <div class="cludo-c-9" role="main">
                            <div class="search-result-count"></div>
                            <div class="search-did-you-mean"></div>
                            <div class="search-results"></div>
                        </div> 
                    </div>
                </div>
            </vs-col>
        </vs-row>
    </vs-container>
</#macro>