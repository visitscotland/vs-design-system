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
	</script>
</@hst.headContribution>