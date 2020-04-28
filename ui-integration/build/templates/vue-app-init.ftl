<@hst.headContribution category="htmlBodyEnd">
    <script src="https://unpkg.com/vue@2.6.10/dist/vue.js"></script>
</@hst.headContribution>

<@hst.headContribution category="htmlBodyEnd">
    <script src="https://unpkg.com/vuex@3.1.1/dist/vuex.js"></script>
</@hst.headContribution>

<@hst.headContribution category="htmlBodyEnd">
    <script type="text/javascript">
        let vs = {
            stores: {},
            app: null
        }
    </script>
</@hst.headContribution>

<@hst.headContribution category="htmlAppInit">
    <script type="text/javascript">
        vs.app = new Vue({
            el: '[data-vue-app-init]',
            data:function() {
                return { test: {} }
            },
            comments: true
        })
    </script>
</@hst.headContribution>