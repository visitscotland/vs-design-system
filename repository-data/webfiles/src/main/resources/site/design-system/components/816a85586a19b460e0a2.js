!function(t,e){"object"==typeof exports&&"object"==typeof module?module.exports=e(require("Vue")):"function"==typeof define&&define.amd?define(["Vue"],e):"object"==typeof exports?exports.FavouritesToggleButton=e(require("Vue")):t.FavouritesToggleButton=e(t.Vue)}(window,function(t){return(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[145,116,117,118,121,122,123,125,126,137,138,140,141,142,156,158,166,172,177,178,181,195,196,197,199,201,205,220,221,222,223,224],{"+6Hs":function(t,e,n){"use strict";var o=n("ulM+");n.d(e,"a",function(){return o.a}),n.d(e,"b",function(){return o.b})},WjXL:function(t,e,n){"use strict";n.r(e);var o=n("+6Hs"),i=n("s9gM"),r=(n("kLwi"),n("KHd+")),u=n("sf6L"),a=n.n(u),s=Object(r.a)(i.a,o.a,o.b,!1,null,"8bc7640e",null);"function"==typeof a.a&&a()(s),s.options.__file="src/components/patterns/favourites/FavouritesToggleButton.vue",e.default=s.exports},YvWA:function(t,e,n){},"i7/w":function(e,n){e.exports=t},kLwi:function(t,e,n){"use strict";var o=n("YvWA");n.n(o).a},lQgh:function(t,e,n){"use strict";n("x0AG");var o=n("oqxP"),i=n("6v0H");e.a={name:"VsFavouritesToggleButton",status:"prototype",release:"0.0.1",components:{VsIcon:o.default},props:{title:{type:String,default:""},href:{type:String,default:""}},computed:{favourites:function(){return i.default.getters["favourites/getFavourites"]},favouriteItem:function(){return{title:this.title,href:this.href}},favourited:function(){var t=this;return-1!==this.favourites.findIndex(function(e){return e.href===t.favouriteItem.href})}},methods:{toggleFavourite:function(){this.favourited?i.default.dispatch("favourites/deleteFavourite",this.favouriteItem):i.default.dispatch("favourites/addFavourite",this.favouriteItem)}}}},s9gM:function(t,e,n){"use strict";var o=n("lQgh");e.a=o.a},sf6L:function(t,e){},"ulM+":function(t,e,n){"use strict";n.d(e,"a",function(){return o}),n.d(e,"b",function(){return i});var o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("VsButton",{staticClass:"vs-favourites-toggle__button",attrs:{variant:"transparent",animate:!1,size:"sm"},nativeOn:{click:function(e){return t.toggleFavourite(e)}}},[t.favourited?n("span",{staticClass:"sr-only"},[t._v("Remove from Favourites")]):n("span",{staticClass:"sr-only"},[t._v("Add to Favourites")]),t._v(" "),t.favourited?n("VsIcon",{attrs:{name:"favourite-filled",size:"md",variant:"primary",padding:0}}):n("VsIcon",{attrs:{name:"favourite",size:"md",variant:"dark",padding:0}})],1)},i=[];o._withStripped=!0}},[["WjXL",0,1,2,3,5,4,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,24,27,35,37,39,38,41,53,73,36,42,69]]])});