!function(t,e){"object"==typeof exports&&"object"==typeof module?module.exports=e(require("Vue")):"function"==typeof define&&define.amd?define(["Vue"],e):"object"==typeof exports?exports.Itinerary=e(require("Vue")):t.Itinerary=e(t.Vue)}(window,function(t){return(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[173,116,117,118,121,122,123,125,126,137,138,140,141,142,156,158,166,172,177,178,181,195,196,197,199,201,205,220,221,222,223,224],{"1hWs":function(t,e,n){"use strict";n.r(e);var i=n("IjJW"),o=n("TZ3Y"),s=(n("iaQj"),n("KHd+")),r=n("OcwV"),a=n.n(r),c=Object(s.a)(o.a,i.a,i.b,!1,null,"ff3766ae",null);"function"==typeof a.a&&a()(c),c.options.__file="src/components/patterns/itineraries/Itinerary.vue",e.default=c.exports},"28RD":function(t,e,n){"use strict";n.d(e,"a",function(){return i}),n.d(e,"b",function(){return o});var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("section",{staticClass:"vs-itinerary position-sticky"},[n("div",{directives:[{name:"show",rawName:"v-show",value:!t.isDesktop&&t.withinItineraryMain,expression:"!isDesktop && withinItineraryMain"}],staticClass:"fixed-bottom"},[n("div",{staticClass:"d-flex justify-content-center pb-2"},[n("VsItineraryMobileMapToggle",{nativeOn:{click:function(e){return t.toggleShowMap()}}})],1)]),t._v(" "),n("div",{directives:[{name:"show",rawName:"v-show",value:t.isDesktop||t.showMap,expression:"isDesktop || showMap"}],staticClass:"vs-itinerary__map-container"},[t._t("map")],2),t._v(" "),n("VsContainer",[n("VsRow",[n("VsCol",{staticClass:"list-unstyled p-0",attrs:{cols:"12",tag:"ul"}},[t._t("list")],2)],1)],1)],1)},o=[];i._withStripped=!0},CPRd:function(t,e,n){"use strict";var i=n("GnPe"),o=n("CeTQ");e.a={name:"VsItinerary",status:"prototype",release:"0.0.1",components:{VsContainer:i.b,VsRow:i.c,VsCol:i.a,VsItineraryMobileMapToggle:o.default},data:function(){return{showMap:!1,isDesktop:!1,withinItineraryMain:!1}},mounted:function(){if(window){var t=document.querySelector(".vds-example");window.addEventListener("resize",this.onResize),null===t?window.addEventListener("scroll",this.onScroll):t.addEventListener("scroll",this.onScroll)}},destroyed:function(){window&&window.removeEventListener("resize",this.onResize)},methods:{onResize:function(){this.isDesktop=window.innerWidth>=1200,this.showMap=window.innerWidth>=1200},onScroll:function(){var t=this.$el.getBoundingClientRect(),e=t.top<=(window.innerHeight||document.documentElement.clientHeight),n=t.bottom<=(window.innerHeight||document.documentElement.clientHeight);this.withinItineraryMain=!(!e||n)},toggleShowMap:function(){this.showMap=!this.showMap}}}},IjJW:function(t,e,n){"use strict";var i=n("28RD");n.d(e,"a",function(){return i.a}),n.d(e,"b",function(){return i.b})},MJws:function(t,e,n){},OcwV:function(t,e){},TZ3Y:function(t,e,n){"use strict";var i=n("CPRd");e.a=i.a},"i7/w":function(e,n){e.exports=t},iaQj:function(t,e,n){"use strict";var i=n("MJws");n.n(i).a}},[["1hWs",0,1,2,3,5,4,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,26,25,27,29,28,30,31,32,33,35,34,37,39,38,40,41,43,44,45,46,47,48,50,52,56,57,36,42,49,51,54,55,58,104]]])});