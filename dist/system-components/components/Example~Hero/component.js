(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[12],{HsRQ:function(t,e,i){},"J/25":function(t,e){},JA5a:function(t,e,i){"use strict";var n=i("HsRQ");i.n(n).a},kREC:function(t,e,i){"use strict";i.r(e);var n=i("oFCD"),a=i("LvDl"),l={short:200,medium:500,tall:700},s={top:"start",middle:"center",bottom:"end"},r={name:"VsHero",status:"prototype",release:"0.0.1",components:{VsHeading:n.default},props:{type:{type:String,default:"section"},imageSrc:{type:String},imageAlignV:{type:String},height:{type:String,default:"medium",validator:function(t){return t.match(/(short|medium|tall)/)}},displayText:{type:String},displayTextSub:{type:String},displayAlignV:{type:String,default:"middle",validator:function(t){return t.match(/(top|middle|bottom)/)}},displayAlignH:{type:String,default:"center",validator:function(t){return t.match(/(left|center|right)/)}}},computed:{bgdImgStyleValue:function(){return this.imageSrc?"url('"+this.imageSrc+"')":"none"},bgdPosStyleValue:function(){},heightPixels:function(){return Object(a.get)(l,this.height)},displayAlignVValue:function(){return Object(a.get)(s,this.displayAlignV)}}},o=(i("JA5a"),i("KHd+")),u=i("J/25"),d=i.n(u),c=Object(o.a)(r,function(){var t=this,e=t.$createElement,i=t._self._c||e;return i(t.type,{tag:"component",staticClass:"hero",style:{backgroundImage:t.bgdImgStyleValue,backgroundPositionY:t.imageAlignV,height:t.heightPixels+"px"}},[i("vs-container",[i("vs-row",{attrs:{"align-v":t.displayAlignVValue}},[i("vs-col",[t.displayText?i("vs-heading",{class:"text-"+t.displayAlignH,attrs:{display:"3",sub:t.displayTextSub}},[t._v(t._s(t.displayText)+"\n        ")]):t._e()],1)],1)],1)],1)},[],!1,null,"6bf62886",null);"function"==typeof d.a&&d()(c),c.options.__file="Hero.vue";e.default=c.exports}}]);