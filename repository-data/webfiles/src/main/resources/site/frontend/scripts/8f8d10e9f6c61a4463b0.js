!function(t,e){"object"==typeof exports&&"object"==typeof module?module.exports=e():"function"==typeof define&&define.amd?define([],e):"object"==typeof exports?exports.VsSiteNavListPromoItem=e():t.VsSiteNavListPromoItem=e()}(window,function(){return(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[185],{"/IhY":function(t,e,i){},MWRS:function(t,e,i){"use strict";i("sMBO");var n=i("LvDl"),s=i("7Asr"),r=i("fpCe");e.a={name:"VsSiteNavListPromoItem",status:"prototype",release:"0.1.0",components:{VsIcon:s.a,VsSiteNavListItem:r.default},props:{imageHref:{type:String,default:""}},computed:{level:function(){return this.$parent.level},backgroundImgStyle:function(){return!!this.isFirstSiblingWithImage&&{backgroundImage:"url(".concat(this.imageHref,")")}},isFirstSiblingWithImage:function(){var t=this;if(!this.imageHref)return!1;var e=Object(n.find)(this.$parent.$children,function(e){return e.$options.name===t.$options.name&&e.imageHref});return this.uid===Object(n.get)(e,"_uid")}}}},OUgS:function(t,e,i){"use strict";var n=i("MWRS");e.a=n.a},OaeR:function(t,e,i){"use strict";var n=i("k9TB");i.d(e,"a",function(){return n.a}),i.d(e,"b",function(){return n.b})},ifJr:function(t,e,i){"use strict";var n=i("/IhY");i.n(n).a},k9TB:function(t,e,i){"use strict";i.d(e,"a",function(){return n}),i.d(e,"b",function(){return s});var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("VsSiteNavListItem",t._b({staticClass:"vs-site-nav__list-promo-item position-relative",class:{"vs-site-nav__list-promo-item--image":t.isFirstSiblingWithImage},style:t.backgroundImgStyle,attrs:{"data-test":"site-promo-list-item"}},"VsSiteNavListItem",t.$attrs,!1),[i("div",{staticClass:"vs-site-nav__list-promo-item-image-copy"},[t._t("default"),t._v(" "),t.isFirstSiblingWithImage?i("VsIcon",{attrs:{"data-test":"mobile-promo-arrow-icon",name:"reverse-arrow",size:"sm",variant:"primary",padding:0}}):t._e()],2)])},s=[];n._withStripped=!0},tmlx:function(t,e){},vscr:function(t,e,i){"use strict";i.r(e);var n=i("OaeR"),s=i("OUgS"),r=(i("ifJr"),i("KHd+")),a=i("tmlx"),o=i.n(a),c=Object(r.a)(s.a,n.a,n.b,!1,null,"4e62d820",null);"function"==typeof o.a&&o()(c),c.options.__file="src/components/patterns/header/components/site-navigation/SiteNavListPromoItem.vue",e.default=c.exports}},[["vscr",0,1,2,4,3,5,6,7,8,9,10,12,13,11,14,15,16,17,18,19,21,22,23,26,27,28,29,30,31,20,24,25,32,42,43,47,49,53,63]]])});