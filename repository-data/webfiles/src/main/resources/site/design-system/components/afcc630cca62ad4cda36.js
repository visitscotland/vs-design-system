!function(t,e){"object"==typeof exports&&"object"==typeof module?module.exports=e(require("Vue")):"function"==typeof define&&define.amd?define(["Vue"],e):"object"==typeof exports?exports.SiteSearch=e(require("Vue")):t.SiteSearch=e(t.Vue)}(window,function(t){return(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[202,116,117,118,121,122,123,125,126,137,138,140,141,142,156,158,166,172,177,178,181,195,196,197,199,201,205,220,221,222,223,224],{"0Hf0":function(t,e,a){"use strict";var n=a("hibV");a.d(e,"a",function(){return n.a}),a.d(e,"b",function(){return n.b})},"6/9+":function(t,e,a){},"7sph":function(t,e){},GwyD:function(t,e,a){"use strict";var n=a("KAPD");e.a=n.a},KAPD:function(t,e,a){"use strict";var n=a("oqxP"),s=a("nprW"),i=a("Ed67"),r=a("MBD0"),o=a("I6a5");e.a={name:"VsSiteSearch",status:"prototype",release:"0.0.1",components:{VsIcon:n.default,BForm:i.a,VsFormInput:s.default,BFormInvalidFeedback:r.a},props:{labelText:{type:String,default:"Enter a search term"},clearButtonText:{type:String,default:"Clear"},submitButtonText:{type:String,default:"Go"},validationText:{type:String,default:"Please enter a search term."}},data:function(){return{searchTerm:"",validated:null}},computed:{drawerModule:function(){return o.getters["drawer/module"]},isValid:function(){return this.searchTerm.length>0}},watch:{drawerModule:function(t){"site-search"!==t&&(this.clearSearchField(),this.resetValidation())}},methods:{clearSearchField:function(){this.searchTerm=""},focusOnInput:function(){this.$refs.searchInput.$el.focus()},clearSearchFieldAndFocus:function(){this.clearSearchField(),this.focusOnInput()},onSubmit:function(t){return!!this.isValid||(t.preventDefault(),this.validated=!1,!1)},onInput:function(){this.validated=!!this.isValid&&null},resetValidation:function(){this.validated=null}}}},"M9+i":function(t,e,a){"use strict";a.r(e);var n=a("0Hf0"),s=a("GwyD"),i=(a("W34z"),a("KHd+")),r=a("7sph"),o=a.n(r),c=Object(i.a)(s.a,n.a,n.b,!1,null,"297c7a7c",null);"function"==typeof o.a&&o()(c),c.options.__file="src/components/patterns/site-search/SiteSearch.vue",e.default=c.exports},W34z:function(t,e,a){"use strict";var n=a("6/9+");a.n(n).a},hibV:function(t,e,a){"use strict";a.d(e,"a",function(){return n}),a.d(e,"b",function(){return s});var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("BForm",{staticClass:"d-flex align-items-start py-2 py-md-4",attrs:{role:"search",action:"",method:"get",novalidate:!0,validated:t.validated,tabindex:"-1"},on:{focus:t.focusOnInput,submit:t.onSubmit}},[a("div",{staticClass:"d-flex flex-column flex-grow-1 position-relative"},[a("label",{staticClass:"position-absolute vs-site-search__label m-0",attrs:{for:"search-input"}},[a("span",{staticClass:"sr-only"},[t._v(t._s(t.labelText))]),t._v(" "),a("VsIcon",{attrs:{name:"search",size:"sm",variant:"secondary"}})],1),t._v(" "),a("VsFormInput",{ref:"searchInput",staticClass:"px-9 vs-site-search__input",attrs:{type:"search",placeholder:t.labelText,autocomplete:"off",state:t.validated,id:"search-input"},nativeOn:{input:function(e){return t.onInput(e)}},model:{value:t.searchTerm,callback:function(e){t.searchTerm=e},expression:"searchTerm"}}),t._v(" "),!1===t.validated?a("BFormInvalidFeedback",{attrs:{state:t.validated}},[t._v("\n            "+t._s(t.validationText)+"\n        ")]):t._e(),t._v(" "),t.searchTerm.length?a("div",{staticClass:"position-absolute vs-site-search__clear-container"},[a("VsButton",{staticClass:"px-1",attrs:{variant:"transparent",type:"button",size:"sm",animate:!1},nativeOn:{click:function(e){return e.preventDefault(),t.clearSearchFieldAndFocus()}}},[a("span",{staticClass:"sr-only-sm-down d-sm-block"},[t._v(t._s(t.clearButtonText))]),t._v(" "),a("VsIcon",{staticClass:"d-sm-none",attrs:{name:"close",size:"xs",variant:"dark"}})],1)],1):t._e()],1),t._v(" "),a("VsButton",{staticClass:"px-md-5",attrs:{type:"submit",variant:"primary"}},[t._v("\n        "+t._s(t.submitButtonText)+"\n    ")])],1)},s=[];n._withStripped=!0},"i7/w":function(e,a){e.exports=t}},[["M9+i",0,1,2,3,5,4,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,26,25,27,29,28,30,31,32,35,34,37,39,38,40,41,46,53,60,72,114,230,36,42,61,99]]])});