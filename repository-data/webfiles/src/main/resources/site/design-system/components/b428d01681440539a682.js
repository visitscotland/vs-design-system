(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[48],{"GUe+":function(t,e,n){"use strict";n.d(e,"a",function(){return k});n("pNMO"),n("TeQF"),n("QWBl"),n("5DmW"),n("27RR"),n("tkto"),n("FZtP");var r=n("3LMw"),a=n("tC49"),o=n("qZlm"),u=n("Iyau"),l=n("Io6r"),i=n("kGy3"),c=n("ex6f"),s=n("2C+6"),d=n("qlm0");function p(t,e){var n=Object.keys(t);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(t);e&&(r=r.filter(function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable})),n.push.apply(n,r)}return n}function b(t,e,n){return e in t?Object.defineProperty(t,e,{value:n,enumerable:!0,configurable:!0,writable:!0}):t[e]=n,t}var f="BButton",O={block:{type:Boolean,default:!1},disabled:{type:Boolean,default:!1},size:{type:String,default:function(){return Object(l.b)(f,"size")}},variant:{type:String,default:function(){return Object(l.b)(f,"variant")}},type:{type:String,default:"button"},tag:{type:String,default:"button"},pill:{type:Boolean,default:!1},squared:{type:Boolean,default:!1},pressed:{type:Boolean,default:null}},y=Object(d.b)();delete y.href.default,delete y.to.default;var g=Object(s.h)(y),j=function(t){for(var e=1;e<arguments.length;e++){var n=null!=arguments[e]?arguments[e]:{};e%2?p(n,!0).forEach(function(e){b(t,e,n[e])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(n)):p(n).forEach(function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(n,e))})}return t}({},y,{},O),v=function(t){"focusin"===t.type?Object(i.a)(t.target,"focus"):"focusout"===t.type&&Object(i.o)(t.target,"focus")},w=function(t){return Boolean(t.href||t.to||t.tag&&"a"===String(t.tag).toLowerCase())},m=function(t){return Object(c.a)(t.pressed)},P=function(t){return!w(t)&&(!t.tag||"button"===String(t.tag).toLowerCase())},h=function(t){var e;return["btn-".concat(t.variant||Object(l.b)(f,"variant")),(e={},b(e,"btn-".concat(t.size),Boolean(t.size)),b(e,"btn-block",t.block),b(e,"rounded-pill",t.pill),b(e,"rounded-0",t.squared&&!t.pill),b(e,"disabled",t.disabled),b(e,"active",t.pressed),e)]},B=function(t){return w(t)?Object(o.a)(g,t):null},S=function(t,e){var n=P(t),r=w(t),a=m(t),o=function(t){return!w(t)&&!P(t)}(t),u=e.attrs&&e.attrs.role?e.attrs.role:null,l=e.attrs?e.attrs.tabindex:null;return o&&(l="0"),{type:n&&!r?t.type:null,disabled:n?t.disabled:null,role:o?"button":u,"aria-disabled":o?String(t.disabled):null,"aria-pressed":a?String(t.pressed):null,autocomplete:a?"off":null,tabindex:t.disabled&&!n?"-1":l}},k=r.a.extend({name:f,functional:!0,props:j,render:function(t,e){var n=e.props,r=e.data,o=e.listeners,l=e.children,i=m(n),s=w(n),p={click:function(t){n.disabled&&Object(c.c)(t)?(t.stopPropagation(),t.preventDefault()):i&&o&&o["update:pressed"]&&Object(u.b)(o["update:pressed"]).forEach(function(t){Object(c.d)(t)&&t(!n.pressed)})}};i&&(p.focusin=v,p.focusout=v);var b={staticClass:"btn",class:h(n),props:B(n),attrs:S(n,r),on:p};return t(s?d.a:n.tag,Object(a.mergeData)(r,b),l)}})}}]);