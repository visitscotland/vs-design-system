<#include "./main-client.ftl">
<#include "./core.ftl">

<@hst.headContribution category="htmlBodyEndScriptsFirst">
    <script src="https://unpkg.com/vue@2.6.10/dist/vue.min.js"></script>
</@hst.headContribution>

<@hst.headContribution category="htmlBodyEndScriptsFirst">
    <script src="https://unpkg.com/vuex@3.1.1/dist/vuex.min.js"></script>
</@hst.headContribution>

<@hst.headContribution category="htmlBodyEndScriptsFirst">
	<script type="text/javascript">
        // initialise global vs object
		vs = {
            stores: {}
        }

        // remove no-js class 
        var elements = document.getElementsByClassName("no-js")

        if(elements.length > 0) {
            elements[0].className = elements[0].className.replace(/(\\s|^)no-js(\\s|$)/, "")
        }
	</script>
</@hst.headContribution>