(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[94],{"8q1j":function(t,e,n){"use strict";var r=n("OJvI");n.d(e,"a",function(){return r.a}),n.d(e,"b",function(){return r.b})},OJvI:function(t,e,n){"use strict";n.d(e,"a",function(){return r}),n.d(e,"b",function(){return s});var r=function(){var t=this,e=t.$createElement;return(t._self._c||e)(t.tag,t._b({ref:"self",tag:"Component",staticClass:"vs-drawer-toggle",attrs:{"aria-expanded":t.contentIsVisible,"is-on":t.contentIsVisible},nativeOn:{click:function(e){return e.preventDefault(),t.toggleContent(e)}}},"Component",t.$attrs,!1),[t._t("default")],2)},s=[];r._withStripped=!0},X9TI:function(t,e,n){"use strict";var r=n("k39r");e.a=r.a},iU7h:function(t,e,n){"use strict";n.r(e);var r=n("8q1j"),s=n("X9TI"),o=n("KHd+"),a=Object(o.a)(s.a,r.a,r.b,!1,null,null,null);a.options.__file="src/components/patterns/drawer/DrawerToggle.vue",e.default=a.exports},k39r:function(t,e,n){"use strict";var r=n("GdDg"),s=n("I6a5"),o=n("0WNZ"),a=n("U06+");e.a={name:"VsDrawerToggle",components:{VsButton:r.a},props:{tag:{type:String,default:"vs-button"},drawerKey:{type:String,required:!0},contentKey:{type:String,required:!0}},computed:{contentIsVisible:function(){return s.getters.getters["drawer/".concat(o.c)](this.contentKey,this.drawerKey)}},methods:{closeDrawer:function(){return s.getters.dispatch("drawer/".concat(a.a),{drawerKey:this.drawerKey}).then(this.focusSelf)},showContent:function(){return s.getters.dispatch("drawer/".concat(a.c),{drawerKey:this.drawerKey,contentKey:this.contentKey,returnFocusElement:this.$refs.self})},toggleContent:function(){this.contentIsVisible?this.closeDrawer():this.showContent()},focusSelf:function(){this.$refs.self.$el.focus()}}}}}]);