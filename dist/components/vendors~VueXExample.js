(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[122],{"6uA0":function(t,e,i){"use strict";e.__esModule=!0,e.default=void 0;var n={props:{id:{type:String,default:null}},data:function(){return{localId_:null}},computed:{safeId:function(){var t=this.id||this.localId_;return function(e){return t?(e=String(e||"").replace(/\s+/g,"_"))?t+"_"+e:t:null}}},mounted:function(){var t=this;this.$nextTick(function(){t.localId_="__BVID__".concat(t._uid)})}};e.default=n},"7+OJ":function(t,e,i){"use strict";e.__esModule=!0,e.default=e.FormCheckboxPlugin=void 0;var n=i("vbH8");e.BFormCheckbox=n.BFormCheckbox;var o=i("Eau4");e.BFormCheckboxGroup=o.BFormCheckboxGroup;var r=(0,i("Co2t").pluginFactory)({components:{BFormCheckbox:n.BFormCheckbox,BCheckbox:n.BFormCheckbox,BCheck:n.BFormCheckbox,BFormCheckboxGroup:o.BFormCheckboxGroup,BCheckboxGroup:o.BFormCheckboxGroup,BCheckGroup:o.BFormCheckboxGroup}});e.FormCheckboxPlugin=r;var s=r;e.default=s},Co2t:function(t,e,i){"use strict";e.__esModule=!0,e.vueUse=e.registerDirectives=e.registerDirective=e.registerComponents=e.registerComponent=e.registerPlugins=e.pluginFactory=e.installFactory=e.checkMultipleVue=void 0;var n=u(i("LRTS")),o=u(i("k+DF")),r=i("L1RV"),s=i("RWVW");function u(t){return t&&t.__esModule?t:{default:t}}function a(t,e){var i=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter(function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable})),i.push.apply(i,n)}return i}function l(t){for(var e=1;e<arguments.length;e++){var i=null!=arguments[e]?arguments[e]:{};e%2?a(i,!0).forEach(function(e){c(t,e,i[e])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(i)):a(i).forEach(function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(i,e))})}return t}function c(t,e,i){return e in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}var d,f,h=(d=!1,f=["Multiple instances of Vue detected!","You may need to set up an alias for Vue in your bundler config.","See: https://bootstrap-vue.js.org/docs#using-module-bundlers"].join("\n"),function(t){d||n.default===t||s.isJSDOM||(0,o.default)(f),d=!0});e.checkMultipleVue=h;var p=function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},e=t.components,i=t.directives,n=t.plugins,o=function t(o){var s=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};t.installed||(t.installed=!0,h(o),(0,r.setConfig)(s,o),b(o,e),y(o,i),v(o,n))};return o.installed=!1,o};e.installFactory=p;e.pluginFactory=function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};return l({},e,{install:p(t)})};var v=function(t){var e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};for(var i in e)i&&e[i]&&t.use(e[i])};e.registerPlugins=v;var m=function(t,e,i){t&&e&&i&&t.component(e,i)};e.registerComponent=m;var b=function(t){var e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};for(var i in e)m(t,i,e[i])};e.registerComponents=b;var g=function(t,e,i){t&&e&&i&&t.directive(e.replace(/^VB/,"B"),i)};e.registerDirective=g;var y=function(t){var e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};for(var i in e)g(t,i,e[i])};e.registerDirectives=y;e.vueUse=function(t){s.hasWindowSupport&&window.Vue&&window.Vue.use(t),s.hasWindowSupport&&t.NAME&&(window[t.NAME]=t)}},Eau4:function(t,e,i){"use strict";e.__esModule=!0,e.default=e.BFormCheckboxGroup=e.props=void 0;var n=c(i("LRTS")),o=c(i("6uA0")),r=c(i("Xwe6")),s=c(i("Rhxi")),u=c(i("XAKr")),a=c(i("bhsD")),l=c(i("QftK"));function c(t){return t&&t.__esModule?t:{default:t}}var d={switches:{type:Boolean,default:!1},checked:{type:[String,Number,Object,Array,Boolean],default:null}};e.props=d;var f=n.default.extend({name:"BFormCheckboxGroup",mixins:[o.default,r.default,u.default,s.default,a.default,l.default],provide:function(){return{bvCheckGroup:this}},props:d,data:function(){return{localChecked:this.checked||[]}},computed:{isRadioGroup:function(){return!1}}});e.BFormCheckboxGroup=f;var h=f;e.default=h},FhHd:function(t,e,i){"use strict";e.__esModule=!0,e.position=e.offset=e.getSel=e.getCS=e.getBCR=e.hasAttr=e.getAttr=e.removeAttr=e.setAttr=e.hasClass=e.removeClass=e.addClass=e.getById=e.contains=e.closest=e.matches=e.select=e.selectAll=e.reflow=e.isDisabled=e.isVisible=e.isElement=e.eventOff=e.eventOn=e.parseEventOptions=e.MutationObs=e.requestAF=e.closestEl=e.matchesEl=void 0;var n=i("uy80"),o=i("RWVW"),r=i("DVoA"),s=o.hasWindowSupport?window:{},u=o.hasDocumentSupport?document:{},a="undefined"!=typeof Element?Element.prototype:{},l=a.matches||a.msMatchesSelector||a.webkitMatchesSelector;e.matchesEl=l;var c=a.closest||function(t){var e=this;do{if(v(e,t))return e;e=e.parentElement||e.parentNode}while(!(0,r.isNull)(e)&&e.nodeType===Node.ELEMENT_NODE);return null};e.closestEl=c;var d=s.requestAnimationFrame||s.webkitRequestAnimationFrame||s.mozRequestAnimationFrame||s.msRequestAnimationFrame||s.oRequestAnimationFrame||function(t){return setTimeout(t,16)};e.requestAF=d;var f=s.MutationObserver||s.WebKitMutationObserver||s.MozMutationObserver||null;e.MutationObs=f;var h=function(t){return o.hasPassiveEventSupport?(0,r.isObject)(t)?t:{useCapture:Boolean(t||!1)}:Boolean((0,r.isObject)(t)?t.useCapture:t)};e.parseEventOptions=h;e.eventOn=function(t,e,i,n){t&&t.addEventListener&&t.addEventListener(e,i,h(n))};e.eventOff=function(t,e,i,n){t&&t.removeEventListener&&t.removeEventListener(e,i,h(n))};var p=function(t){return Boolean(t&&t.nodeType===Node.ELEMENT_NODE)};e.isElement=p;e.isVisible=function(t){if(!p(t)||!m(u.body,t))return!1;if("none"===t.style.display)return!1;var e=y(t);return Boolean(e&&e.height>0&&e.width>0)};e.isDisabled=function(t){return!p(t)||t.disabled||Boolean(g(t,"disabled"))||b(t,"disabled")};e.reflow=function(t){return p(t)&&t.offsetHeight};e.selectAll=function(t,e){return(0,n.from)((p(e)?e:u).querySelectorAll(t))};e.select=function(t,e){return(p(e)?e:u).querySelector(t)||null};var v=function(t,e){return!!p(t)&&l.call(t,e)};e.matches=v;e.closest=function(t,e){if(!p(e))return null;var i=c.call(e,t);return i===e?null:i};var m=function(t,e){return!(!t||!(0,r.isFunction)(t.contains))&&t.contains(e)};e.contains=m;e.getById=function(t){return u.getElementById(/^#/.test(t)?t.slice(1):t)||null};e.addClass=function(t,e){e&&p(t)&&t.classList&&t.classList.add(e)};e.removeClass=function(t,e){e&&p(t)&&t.classList&&t.classList.remove(e)};var b=function(t,e){return!!(e&&p(t)&&t.classList)&&t.classList.contains(e)};e.hasClass=b;e.setAttr=function(t,e,i){e&&p(t)&&t.setAttribute(e,i)};e.removeAttr=function(t,e){e&&p(t)&&t.removeAttribute(e)};var g=function(t,e){return e&&p(t)?t.getAttribute(e):null};e.getAttr=g;e.hasAttr=function(t,e){return e&&p(t)?t.hasAttribute(e):null};var y=function(t){return p(t)?t.getBoundingClientRect():null};e.getBCR=y;var C=function(t){return o.hasWindowSupport&&p(t)?s.getComputedStyle(t):{}};e.getCS=C;e.getSel=function(){return o.hasWindowSupport&&s.getSelection?s.getSelection():null};var k=function(t){var e={top:0,left:0};if(!p(t)||0===t.getClientRects().length)return e;var i=y(t);if(i){var n=t.ownerDocument.defaultView;e.top=i.top+n.pageYOffset,e.left=i.left+n.pageXOffset}return e};e.offset=k;e.position=function(t){var e={top:0,left:0};if(!p(t))return e;var i={top:0,left:0},n=C(t);if("fixed"===n.position)e=y(t)||e;else{e=k(t);for(var o=t.ownerDocument,r=t.offsetParent||o.documentElement;r&&(r===o.body||r===o.documentElement)&&"static"===C(r).position;)r=r.parentNode;if(r&&r!==t&&r.nodeType===Node.ELEMENT_NODE){i=k(r);var s=C(r);i.top+=parseFloat(s.borderTopWidth),i.left+=parseFloat(s.borderLeftWidth)}}return{top:e.top-i.top-parseFloat(n.marginTop),left:e.left-i.left-parseFloat(n.marginLeft)}}},L1RV:function(t,e,i){"use strict";e.__esModule=!0,e.resetConfig=e.setConfig=void 0;var n=c(i("LRTS")),o=c(i("8Y+A")),r=c(i("sN+d")),s=c(i("k+DF")),u=i("DVoA"),a=i("JOL4"),l=c(i("/pFK"));function c(t){return t&&t.__esModule?t:{default:t}}function d(t,e){for(var i=0;i<e.length;i++){var n=e[i];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(t,n.key,n)}}var f=function(){function t(){!function(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}(this,t),this.$_config={},this.$_cachedBreakpoints=null}var e,i,n;return e=t,n=[{key:"Defaults",get:function(){return l.default}}],(i=[{key:"getDefaults",value:function(){return this.defaults}},{key:"setConfig",value:function(){var t=this,e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};if((0,u.isPlainObject)(e)){var i=(0,a.getOwnPropertyNames)(e);i.forEach(function(i){if((0,a.hasOwnProperty)(l.default,i)){var n=e[i];if("breakpoints"===i){var r=e.breakpoints;!(0,u.isArray)(r)||r.length<2||r.some(function(t){return!(0,u.isString)(t)||0===t.length})?(0,s.default)('config: "breakpoints" must be an array of at least 2 breakpoint names'):t.$_config.breakpoints=(0,o.default)(r)}else(0,u.isPlainObject)(n)&&(0,a.getOwnPropertyNames)(n).forEach(function(e){(0,a.hasOwnProperty)(l.default[i],e)?(t.$_config[i]=t.$_config[i]||{},(0,u.isUndefined)(n[e])||(t.$_config[i][e]=(0,o.default)(n[e]))):(0,s.default)('config: unknown config property "'.concat(i,".").concat(e,'"'))})}else(0,s.default)('config: unknown config property "'.concat(i,'"'))})}}},{key:"resetConfig",value:function(){this.$_config={}}},{key:"getConfig",value:function(){return(0,o.default)(this.$_config)}},{key:"getConfigValue",value:function(t){return(0,o.default)((0,r.default)(this.$_config,t,(0,r.default)(l.default,t)))}},{key:"defaults",get:function(){return l.default}}])&&d(e.prototype,i),n&&d(e,n),t}();e.setConfig=function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:n.default;e.prototype.$bvConfig=n.default.prototype.$bvConfig=e.prototype.$bvConfig||n.default.prototype.$bvConfig||new f,e.prototype.$bvConfig.setConfig(t)};e.resetConfig=function(){n.default.prototype.$bvConfig&&n.default.prototype.$bvConfig.resetConfig&&n.default.prototype.$bvConfig.resetConfig()}},LDig:function(t,e,i){"use strict";e.__esModule=!0,e.default=e.BFormRadio=void 0;var n=c(i("LRTS")),o=c(i("6uA0")),r=c(i("Xwe6")),s=c(i("QftK")),u=c(i("bhsD")),a=c(i("T+Gv")),l=c(i("ls0u"));function c(t){return t&&t.__esModule?t:{default:t}}var d=n.default.extend({name:"BFormRadio",mixins:[o.default,a.default,r.default,u.default,s.default],inject:{bvGroup:{from:"bvRadioGroup",default:!1}},props:{checked:{type:[String,Object,Number,Boolean],default:null}},computed:{isChecked:function(){return(0,l.default)(this.value,this.computedLocalChecked)},isRadio:function(){return!0},isCheck:function(){return!1}},watch:{computedLocalChecked:function(t,e){this.$emit("input",this.computedLocalChecked)}},methods:{handleChange:function(t){var e=t.target.checked,i=this.value;this.computedLocalChecked=i,this.$emit("change",e?i:null),this.isGroup&&this.bvGroup.$emit("change",e?i:null)}}});e.BFormRadio=d;var f=d;e.default=f},LY3G:function(t,e,i){"use strict";e.__esModule=!0,e.default=void 0;var n,o=(n=i("ls0u"))&&n.__esModule?n:{default:n};var r=function(t,e){for(var i=0;i<t.length;i++)if((0,o.default)(t[i],e))return i;return-1};e.default=r},QftK:function(t,e,i){"use strict";e.__esModule=!0,e.default=void 0;var n={props:{state:{type:[String,Boolean],default:null}},computed:{computedState:function(){var t=this.state;return""===t?null:!0===t||"valid"===t||!1!==t&&"invalid"!==t&&null},stateClass:function(){var t=this.computedState;return!0===t?"is-valid":!1===t?"is-invalid":null}}};e.default=n},RWVW:function(t,e,i){"use strict";(function(t){e.__esModule=!0,e.getNoWarn=e.getEnv=e.hasIntersectionObserverSupport=e.hasPointerEventSupport=e.hasTouchSupport=e.hasPassiveEventSupport=e.isIE=e.isJSDOM=e.userAgent=e.isBrowser=e.hasMutationObserverSupport=e.hasPromiseSupport=e.hasNavigatorSupport=e.hasDocumentSupport=e.hasWindowSupport=void 0;var i="undefined"!=typeof window;e.hasWindowSupport=i;var n="undefined"!=typeof document;e.hasDocumentSupport=n;var o="undefined"!=typeof navigator;e.hasNavigatorSupport=o;var r="undefined"!=typeof Promise;e.hasPromiseSupport=r;var s="undefined"!=typeof MutationObserver||"undefined"!=typeof WebKitMutationObserver||"undefined"!=typeof MozMutationObserver;e.hasMutationObserverSupport=s;var u=i&&n&&o;e.isBrowser=u;var a=u?window.navigator.userAgent.toLowerCase():"";e.userAgent=a;var l=a.indexOf("jsdom")>0;e.isJSDOM=l;var c=/msie|trident/.test(a);e.isIE=c;var d=function(){var t=!1;if(u)try{var e={get passive(){t=!0}};window.addEventListener("test",e,e),window.removeEventListener("test",e,e)}catch(e){t=!1}return t}();e.hasPassiveEventSupport=d;var f=u&&("ontouchstart"in document.documentElement||navigator.maxTouchPoints>0);e.hasTouchSupport=f;var h=u&&Boolean(window.PointerEvent||window.MSPointerEvent);e.hasPointerEventSupport=h;var p=u&&"IntersectionObserver"in window&&"IntersectionObserverEntry"in window&&"intersectionRatio"in window.IntersectionObserverEntry.prototype;e.hasIntersectionObserverSupport=p;var v=function(e){var i=arguments.length>1&&void 0!==arguments[1]?arguments[1]:null,n=void 0!==t&&t?Object({NODE_ENV:"production"})||!1:{};return e?n[e]||i:n};e.getEnv=v;e.getNoWarn=function(){return v("BOOTSTRAP_VUE_NO_WARN")}}).call(this,i("8oxB"))},Rhxi:function(t,e,i){"use strict";e.__esModule=!0,e.default=void 0;var n=i("vhGO"),o=i("DVoA"),r=i("JOL4"),s={props:{options:{type:[Array,Object],default:function(){return[]}},valueField:{type:String,default:"value"},textField:{type:String,default:"text"},htmlField:{type:String,default:"html"},disabledField:{type:String,default:"disabled"}},computed:{formOptions:function(){var t=this.options,e=this.valueField,i=this.textField,s=this.htmlField,u=this.disabledField;return(0,o.isArray)(t)?t.map(function(t){if((0,o.isPlainObject)(t)){var r=t[e],a=String(t[i]);return{value:(0,o.isUndefined)(r)?a:r,text:(0,n.stripTags)(a),html:t[s],disabled:Boolean(t[u])}}return{value:t,text:(0,n.stripTags)(String(t)),disabled:!1}}):(0,r.keys)(t).map(function(r){var a=t[r]||{};if((0,o.isPlainObject)(a)){var l=a[e],c=a[i];return{value:(0,o.isUndefined)(l)?r:l,text:(0,o.isUndefined)(c)?(0,n.stripTags)(String(r)):(0,n.stripTags)(String(c)),html:a[s],disabled:Boolean(a[u])}}return{value:r,text:(0,n.stripTags)(String(a)),disabled:!1}})}}};e.default=s},"T+Gv":function(t,e,i){"use strict";var n;function o(t,e){var i=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter(function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable})),i.push.apply(i,n)}return i}function r(t){for(var e=1;e<arguments.length;e++){var i=null!=arguments[e]?arguments[e]:{};e%2?o(i,!0).forEach(function(e){s(t,e,i[e])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(i)):o(i).forEach(function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(i,e))})}return t}function s(t,e,i){return e in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}e.__esModule=!0,e.default=void 0;var u={mixins:[((n=i("Uyqt"))&&n.__esModule?n:{default:n}).default],inheritAttrs:!1,model:{prop:"checked",event:"input"},props:{value:{},checked:{},inline:{type:Boolean,default:!1},plain:{type:Boolean,default:!1},button:{type:Boolean,default:!1},buttonVariant:{type:String,default:null},ariaLabel:{type:String,default:null},ariaLabelledby:{type:String,default:null}},data:function(){return{localChecked:this.isGroup?this.bvGroup.checked:this.checked,hasFocus:!1}},computed:{computedLocalChecked:{get:function(){return this.isGroup?this.bvGroup.localChecked:this.localChecked},set:function(t){this.isGroup?this.bvGroup.localChecked=t:this.localChecked=t}},isGroup:function(){return Boolean(this.bvGroup)},isBtnMode:function(){return this.isGroup?this.bvGroup.buttons:this.button},isPlain:function(){return!this.isBtnMode&&(this.isGroup?this.bvGroup.plain:this.plain)},isCustom:function(){return!this.isBtnMode&&!this.isPlain},isSwitch:function(){return!(this.isBtnMode||this.isRadio||this.isPlain)&&(this.isGroup?this.bvGroup.switches:this.switch)},isInline:function(){return this.isGroup?this.bvGroup.inline:this.inline},isDisabled:function(){return this.isGroup&&this.bvGroup.disabled||this.disabled},isRequired:function(){return Boolean(this.getName&&(this.isGroup?this.bvGroup.required:this.required))},getName:function(){return(this.isGroup?this.bvGroup.groupName:this.name)||null},getForm:function(){return(this.isGroup?this.bvGroup.form:this.form)||null},getSize:function(){return(this.isGroup?this.bvGroup.size:this.size)||""},getState:function(){return this.isGroup?this.bvGroup.computedState:this.computedState},getButtonVariant:function(){return this.buttonVariant?this.buttonVariant:this.isGroup&&this.bvGroup.buttonVariant?this.bvGroup.buttonVariant:"secondary"},buttonClasses:function(){var t;return["btn","btn-".concat(this.getButtonVariant),(t={},s(t,"btn-".concat(this.getSize),this.getSize),s(t,"disabled",this.isDisabled),s(t,"active",this.isChecked),s(t,"focus",this.hasFocus),t)]}},watch:{checked:function(t,e){this.computedLocalChecked=t}},methods:{handleFocus:function(t){t.target&&("focus"===t.type?this.hasFocus=!0:"blur"===t.type&&(this.hasFocus=!1))},focus:function(){!this.isDisabled&&this.$refs.input&&this.$refs.input.focus&&this.$refs.input.focus()},blur:function(){!this.isDisabled&&this.$refs.input&&this.$refs.input.blur&&this.$refs.input.blur()}},render:function(t){var e=this.normalizeSlot("default"),i={change:this.handleChange};this.isBtnMode&&(i.focus=i.blur=this.handleFocus);var n=t("input",{ref:"input",key:"input",on:i,class:{"form-check-input":this.isPlain,"custom-control-input":this.isCustom,"is-valid":!0===this.getState&&!this.isBtnMode,"is-invalid":!1===this.getState&&!this.isBtnMode,"position-static":this.isPlain&&!e},directives:[{name:"model",rawName:"v-model",value:this.computedLocalChecked,expression:"computedLocalChecked"}],attrs:r({},this.$attrs,{id:this.safeId(),type:this.isRadio?"radio":"checkbox",name:this.getName,form:this.getForm,disabled:this.isDisabled,required:this.isRequired,autocomplete:"off","aria-required":this.isRequired||null,"aria-label":this.ariaLabel||null,"aria-labelledby":this.ariaLabelledby||null}),domProps:{value:this.value,checked:this.isChecked}});if(this.isBtnMode){var o=t("label",{class:this.buttonClasses},[n,e]);return this.isGroup||(o=t("div",{class:["btn-group-toggle","d-inline-block"]},[o])),o}var u=t();return this.isPlain&&!e||(u=t("label",{class:{"form-check-label":this.isPlain,"custom-control-label":this.isCustom},attrs:{for:this.safeId()}},e)),t("div",{class:s({"form-check":this.isPlain,"form-check-inline":this.isPlain&&this.isInline,"custom-control":this.isCustom,"custom-control-inline":this.isCustom&&this.isInline,"custom-checkbox":this.isCustom&&this.isCheck&&!this.isSwitch,"custom-switch":this.isSwitch,"custom-radio":this.isCustom&&this.isRadio},"b-custom-control-".concat(this.getSize),Boolean(this.getSize&&!this.isBtnMode))},[n,u])}};e.default=u},Uyqt:function(t,e,i){"use strict";e.__esModule=!0,e.default=void 0;var n=i("necD"),o=i("uy80"),r={methods:{hasNormalizedSlot:function(t){return(0,n.hasNormalizedSlot)(t,this.$scopedSlots,this.$slots)},normalizeSlot:function(t){var e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{},i=(0,n.normalizeSlot)(t,e,this.$scopedSlots,this.$slots);return i?(0,o.concat)(i):i}}};e.default=r},XAKr:function(t,e,i){"use strict";e.__esModule=!0,e.default=void 0;var n,o=i("vhGO"),r=(n=i("Uyqt"))&&n.__esModule?n:{default:n},s=i("vbH8"),u=i("LDig");var a={mixins:[r.default],model:{prop:"checked",event:"input"},props:{validated:{type:Boolean,default:!1},ariaInvalid:{type:[Boolean,String],default:!1},stacked:{type:Boolean,default:!1},plain:{type:Boolean,default:!1},buttons:{type:Boolean,default:!1},buttonVariant:{type:String,default:"secondary"}},computed:{inline:function(){return!this.stacked},groupName:function(){return this.name||this.safeId()},groupClasses:function(){return this.buttons?["btn-group-toggle",this.inline?"btn-group":"btn-group-vertical",this.size?"btn-group-".concat(this.size):"",this.validated?"was-validated":""]:[this.validated?"was-validated":""]},computedAriaInvalid:function(){var t=this.ariaInvalid;return!0===t||"true"===t||""===t?"true":!1===this.computedState?"true":null}},watch:{checked:function(t,e){this.localChecked=t},localChecked:function(t,e){this.$emit("input",t)}},render:function(t){var e=this,i=this.formOptions.map(function(i,n){var r="_BV_option_".concat(n,"_");return t(e.isRadioGroup?u.BFormRadio:s.BFormCheckbox,{key:r,props:{id:e.safeId(r),value:i.value,disabled:i.disabled||!1}},[t("span",{domProps:(0,o.htmlOrText)(i.html,i.text)})])});return t("div",{class:this.groupClasses,attrs:{id:this.safeId(),role:this.isRadioGroup?"radiogroup":"group",tabindex:"-1","aria-required":this.required?"true":null,"aria-invalid":this.computedAriaInvalid}},[this.normalizeSlot("first"),i,this.normalizeSlot("default")])}};e.default=a},Xwe6:function(t,e,i){"use strict";e.__esModule=!0,e.default=void 0;var n=i("FhHd"),o={props:{name:{type:String},id:{type:String},disabled:{type:Boolean},required:{type:Boolean,default:!1},form:{type:String,default:null},autofocus:{type:Boolean,default:!1}},mounted:function(){this.handleAutofocus()},activated:function(){this.handleAutofocus()},methods:{handleAutofocus:function(){var t=this;this.$nextTick(function(){(0,n.requestAF)(function(){var e=t.$el;t.autofocus&&(0,n.isVisible)(e)&&((0,n.matches)(e,"input, textarea, select")||(e=(0,n.select)("input, textarea, select",e)),e&&e.focus&&e.focus())})})}}};e.default=o},bhsD:function(t,e,i){"use strict";e.__esModule=!0,e.default=void 0;var n=i("62DN"),o={props:{size:{type:String,default:function(){return(0,n.getComponentConfig)("formControls","size")}}},computed:{sizeFormClass:function(){return[this.size?"form-control-".concat(this.size):null]},sizeBtnClass:function(){return[this.size?"btn-".concat(this.size):null]}}};e.default=o},"k+DF":function(t,e,i){"use strict";e.__esModule=!0,e.default=e.warnNoMutationObserverSupport=e.warnNoPromiseSupport=e.warnNotClient=e.warn=void 0;var n=i("RWVW"),o=function(t){(0,n.getNoWarn)()||console.warn("[BootstrapVue warn]: ".concat(t))};e.warn=o;e.warnNotClient=function(t){return!n.isBrowser&&(o("".concat(t,": Can not be called during SSR.")),!0)};e.warnNoPromiseSupport=function(t){return!n.hasPromiseSupport&&(o("".concat(t,": Requires Promise support.")),!0)};e.warnNoMutationObserverSupport=function(t){return!n.hasMutationObserverSupport&&(o("".concat(t,": Requires MutationObserver support.")),!0)};var r=o;e.default=r},ls0u:function(t,e,i){"use strict";e.__esModule=!0,e.default=void 0;var n=i("JOL4"),o=i("DVoA"),r=function(t,e){if(t.length!==e.length)return!1;for(var i=!0,n=0;i&&n<t.length;n++)i=s(t[n],e[n]);return i},s=function t(e,i){if(e===i)return!0;var s=(0,o.isDate)(e),u=(0,o.isDate)(i);if(s||u)return!(!s||!u)&&e.getTime()===i.getTime();if(s=(0,o.isArray)(e),u=(0,o.isArray)(i),s||u)return!(!s||!u)&&r(e,i);if(s=(0,o.isObject)(e),u=(0,o.isObject)(i),s||u){if(!s||!u)return!1;if((0,n.keys)(e).length!==(0,n.keys)(i).length)return!1;for(var a in e){var l=e.hasOwnProperty(a),c=i.hasOwnProperty(a);if(l&&!c||!l&&c||!t(e[a],i[a]))return!1}}return String(e)===String(i)},u=s;e.default=u},vbH8:function(t,e,i){"use strict";e.__esModule=!0,e.default=e.BFormCheckbox=void 0;var n=f(i("LRTS")),o=f(i("ls0u")),r=f(i("LY3G")),s=i("DVoA"),u=f(i("Xwe6")),a=f(i("T+Gv")),l=f(i("bhsD")),c=f(i("QftK")),d=f(i("6uA0"));function f(t){return t&&t.__esModule?t:{default:t}}var h=n.default.extend({name:"BFormCheckbox",mixins:[a.default,d.default,u.default,l.default,c.default],inject:{bvGroup:{from:"bvCheckGroup",default:!1}},props:{value:{default:!0},uncheckedValue:{default:!1},indeterminate:{type:Boolean,default:!1},switch:{type:Boolean,default:!1},checked:{type:[String,Number,Object,Array,Boolean],default:null}},computed:{isChecked:function(){var t=this.computedLocalChecked,e=this.value;return(0,s.isArray)(t)?(0,r.default)(t,e)>-1:(0,o.default)(t,e)},isRadio:function(){return!1},isCheck:function(){return!0}},watch:{computedLocalChecked:function(t,e){this.$emit("input",t),this.$refs&&this.$refs.input&&this.$emit("update:indeterminate",this.$refs.input.indeterminate)},indeterminate:function(t,e){this.setIndeterminate(t)}},mounted:function(){this.setIndeterminate(this.indeterminate)},methods:{handleChange:function(t){var e=t.target,i=e.checked,n=e.indeterminate,o=this.computedLocalChecked,u=this.value,a=(0,s.isArray)(o),l=a?null:this.uncheckedValue;if(a){var c=(0,r.default)(o,u);i&&c<0?o=o.concat(u):!i&&c>-1&&(o=o.slice(0,c).concat(o.slice(c+1)))}else o=i?u:l;this.computedLocalChecked=o,this.$emit("change",i?u:l),this.isGroup&&this.bvGroup.$emit("change",o),this.$emit("update:indeterminate",n)},setIndeterminate:function(t){(0,s.isArray)(this.computedLocalChecked)&&(t=!1),this.$refs&&this.$refs.input&&(this.$refs.input.indeterminate=t,this.$emit("update:indeterminate",t))}}});e.BFormCheckbox=h;var p=h;e.default=p}}]);