!function(t,e){"object"==typeof exports&&"object"==typeof module?module.exports=e(require("Vue")):"function"==typeof define&&define.amd?define(["Vue"],e):"object"==typeof exports?exports.SummaryBoxDistanceDisplay=e(require("Vue")):t.SummaryBoxDistanceDisplay=e(t.Vue)}(window,function(t){return(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[209,116,117,118,121,122,123,125,126,137,138,140,141,142,156,158,166,172,177,178,181,195,196,197,199,201,205,220,221,222,223,224],{"0Ibg":function(t,e,i){"use strict";var n=i("np9X");i.d(e,"a",function(){return n.a}),i.d(e,"b",function(){return n.b})},"2kzw":function(t,e,i){"use strict";var n=i("2rS5");e.a=n.a},"2rS5":function(t,e,i){"use strict";var n=i("CS42");e.a={name:"VsSummaryBoxDistanceDisplay",status:"prototype",release:"0.0.1",components:{},props:{miles:{type:String,default:""},kilometres:{type:String,default:""},milesLabel:{type:String,default:""},kilometresLabel:{type:String,default:""}},computed:{isShowingMiles:function(){return n.default.getters["summaryBox/getShowMiles"]}},watch:{isShowingMiles:function(){}},summaryBoxStore:n.default}},"5aId":function(t,e,i){"use strict";var n=i("YfWf");i.n(n).a},TwVT:function(t,e){},YfWf:function(t,e,i){},"i7/w":function(e,i){e.exports=t},kEcL:function(t,e,i){"use strict";i.r(e);var n=i("0Ibg"),s=i("2kzw"),o=(i("5aId"),i("KHd+")),a=i("TwVT"),r=i.n(a),u=Object(o.a)(s.a,n.a,n.b,!1,null,"623e19c4",null);"function"==typeof r.a&&r()(u),u.options.__file="src/components/patterns/summary-box/components/summary-box-distance-display/SummaryBoxDistanceDisplay.vue",e.default=u.exports},np9X:function(t,e,i){"use strict";i.d(e,"a",function(){return n}),i.d(e,"b",function(){return s});var n=function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"vs-summary-box-distance-display position-absolute d-block text-center w-100"},[this.isShowingMiles?e("span",{attrs:{id:"display_miles"}},[this._v(this._s(this.miles))]):e("span",{attrs:{id:"display_kilometres"}},[this._v(this._s(this.kilometres))])])},s=[];n._withStripped=!0}},[["kEcL",0,1,2,3,5,4,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,35,37,53,82]]])});