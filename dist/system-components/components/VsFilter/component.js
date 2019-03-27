!function(t,e){"object"==typeof exports&&"object"==typeof module?module.exports=e():"function"==typeof define&&define.amd?define([],e):"object"==typeof exports?exports.VsFilter=e():t.VsFilter=e()}(window,function(){return function(t){var e={};function r(n){if(e[n])return e[n].exports;var i=e[n]={i:n,l:!1,exports:{}};return t[n].call(i.exports,i,i.exports,r),i.l=!0,i.exports}return r.m=t,r.c=e,r.d=function(t,e,n){r.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:n})},r.r=function(t){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},r.t=function(t,e){if(1&e&&(t=r(t)),8&e)return t;if(4&e&&"object"==typeof t&&t&&t.__esModule)return t;var n=Object.create(null);if(r.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var i in t)r.d(n,i,function(e){return t[e]}.bind(null,i));return n},r.n=function(t){var e=t&&t.__esModule?function(){return t.default}:function(){return t};return r.d(e,"a",e),e},r.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},r.p="/",r(r.s="KYUv")}({"KHd+":function(t,e,r){"use strict";function n(t,e,r,n,i,o,s,l){var u,c="function"==typeof t?t.options:t;if(e&&(c.render=e,c.staticRenderFns=r,c._compiled=!0),n&&(c.functional=!0),o&&(c._scopeId="data-v-"+o),s?(u=function(t){(t=t||this.$vnode&&this.$vnode.ssrContext||this.parent&&this.parent.$vnode&&this.parent.$vnode.ssrContext)||"undefined"==typeof __VUE_SSR_CONTEXT__||(t=__VUE_SSR_CONTEXT__),i&&i.call(this,t),t&&t._registeredComponents&&t._registeredComponents.add(s)},c._ssrRegister=u):i&&(u=l?function(){i.call(this,this.$root.$options.shadowRoot)}:i),u)if(c.functional){c._injectStyles=u;var f=c.render;c.render=function(t,e){return u.call(e),f(t,e)}}else{var a=c.beforeCreate;c.beforeCreate=a?[].concat(a,u):[u]}return{exports:t,options:c}}r.d(e,"a",function(){return n})},KYUv:function(t,e,r){"use strict";r.r(e);var n={name:"VsFilter",props:{label:{type:String,required:!0},filterkey:{type:String,required:!0},items:{type:Array,required:!0}},data:()=>({showDropdown:!1,currentFilters:[]}),mounted:function(){let t=t=>{this.showDropdown&&!this.$el.contains(t.target)&&this.buttonBlur()};window.addEventListener("click",e=>{t(e)}),window.addEventListener("touchend",e=>{t(e)})},methods:{getCurrentFilters:function(){let t=[];return Array.prototype.forEach.call(this.currentFilters,e=>{let r=this.items.filter(t=>t.key===e)[0];r.filterkey=this.filterkey,t.push(r)}),t},toggleFilter:function(t){let e=this.currentFilters.indexOf(t);-1===e?this.currentFilters.push(t):this.currentFilters.splice(e,1),this.$parent.$emit("filterschanged")},removeFilter:function(t){if(t.filterkey!==this.filterkey)return;let e=this.currentFilters.indexOf(t.key);this.currentFilters.splice(e,1),this.$parent.$emit("filterschanged")},buttonBlur:function(){if(!this.showDropdown)return;let t=!1;setTimeout(()=>{Array.prototype.forEach.call(this.$el.querySelectorAll("button"),function(e){e===document.activeElement&&(t=!0)}),t||(this.showDropdown=!1)},50)},isCurrentFilter:function(t){return-1!==this.currentFilters.indexOf(t)}}},i=(r("LY3c"),r("KHd+")),o=Object(i.a)(n,function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"vs-list-filter",attrs:{"data-test":"vs-list-filter-"+t.filterkey}},[r("button",{staticClass:"button button--dropdown button--icon-right",on:{click:function(e){t.showDropdown=!t.showDropdown},blur:function(e){t.buttonBlur()}}},[t._v("\n    "+t._s(t.label)+" "),r("i",{staticClass:"icon icon-chevron-down"})]),t._v(" "),r("div",{staticClass:"vs-list-filter__list",class:[t.showDropdown?"vs-list-filter__list--visible":""]},[r("div",{staticClass:"vs-list-filter__list__arrow"}),t._v(" "),r("ul",t._l(t.items,function(e){return r("li",{key:e.key},[r("button",{on:{click:function(r){t.toggleFilter(e.key)},blur:function(e){t.buttonBlur()}}},[t.isCurrentFilter(e.key)?r("i",{staticClass:"icon icon-tick"}):t._e(),t._v(t._s(e.label)+"\n        ")])])}))])])},[],!1,null,"5a38c340",null);o.options.__file="VsFilter.vue";e.default=o.exports},LY3c:function(t,e,r){"use strict";var n=r("xFkr");r.n(n).a},xFkr:function(t,e,r){}})});