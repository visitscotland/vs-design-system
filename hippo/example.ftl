<#include "../imports.ftl">

<#include "../deps/vue.ftl">

<@hst.headContribution category="htmlBodyEnd">
    <script src="<@hst.webfile path='/design-system/components/Col/component.js'/>"></script>
</@hst.headContribution>

<@hst.headContribution category="htmlHead">
	<link rel="stylesheet" href="<@hst.webfile  path='/design-system/components/Col/component.css'/>" type="text/css"/>
</@hst.headContribution>

<@hst.headContribution category="htmlBodyEnd">
    <script type="text/javascript">
        Vue.component('vs-col', Col.default)
    </script>
</@hst.headContribution>