!function(t,e){"object"==typeof exports&&"object"==typeof module?module.exports=e():"function"==typeof define&&define.amd?define([],e):"object"==typeof exports?exports.VsFavouritesViewButton=e():t.VsFavouritesViewButton=e()}(window,function(){return(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[131],{"36pS":function(t,e,n){"use strict";n.r(e);var o=n("7TSv"),s=n("ncTg"),a=(n("V1A5"),n("KHd+")),r=n("mNGf"),i=n.n(r),u=Object(a.a)(s.a,o.a,o.b,!1,null,"37ad0eca",null);"function"==typeof i.a&&i()(u),u.options.__file="src/components/patterns/favourites/FavouritesViewButton.vue",e.default=u.exports},"7TSv":function(t,e,n){"use strict";var o=n("ic9r");n.d(e,"a",function(){return o.a}),n.d(e,"b",function(){return o.b})},ASTq:function(t,e,n){"use strict";var o=n("oqxP"),s=n("6v0H");e.a={name:"VsFavouritesViewButton",status:"prototype",release:"0.0.1",components:{VsIcon:o.default},props:{},computed:{favouritesCount:function(){return s.default.getters["favourites/getFavouritesCount"]},favourites:function(){return s.default.getters["favourites/getFavourites"]}}}},V1A5:function(t,e,n){"use strict";var o=n("deEq");n.n(o).a},deEq:function(t,e,n){},ic9r:function(t,e,n){"use strict";n.d(e,"a",function(){return o}),n.d(e,"b",function(){return s});var o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return t.favouritesCount>0?n("VsButton",{staticClass:"vs-favourites-view__button p-0 p-sm-1 position-relative",attrs:{variant:"transparent",animate:!1},nativeOn:{click:function(e){return t.openFavourites(e)}}},[n("span",{staticClass:"sr-only"},[t._v("View favourites")]),t._v(" "),t.favouritesCount>0?n("span",{staticClass:"vs-favourites-view__button__count"},[n("span",{staticClass:"sr-only"},[t._v("Current favourites count:")]),t._v(" "+t._s(t.favouritesCount)+"\n    ")]):t._e(),t._v(" "),t.favouritesCount>0?n("VsIcon",{attrs:{name:"favourite-filled",size:"sm",variant:"primary"}}):n("VsIcon",{attrs:{name:"favourite",size:"sm",variant:"dark"}})],1):t._e()},s=[];o._withStripped=!0},mNGf:function(t,e){},ncTg:function(t,e,n){"use strict";var o=n("ASTq");e.a=o.a}},[["36pS",0,1,2,4,3,5,6,7,8,14,16,22,23,40,56,20,24,25,52]]])});