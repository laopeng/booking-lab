webpackJsonp([5],{"8a1r":function(e,t){},NHnr:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});n("tvR6");var r=n("qBF2"),o=n.n(r),a=n("7+uW"),s={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{attrs:{id:"app"}},[t("router-view")],1)},staticRenderFns:[]};var u=n("VU/8")({name:"App"},s,!1,function(e){n("8a1r")},null,null).exports,c=n("/ocq");a.default.use(c.a);var i=new c.a({routes:[{path:"/",name:"Login",component:function(e){return n.e(3).then(function(){var t=[n("xJsL")];e.apply(null,t)}.bind(this)).catch(n.oe)}},{path:"/labStatus",component:function(e){return n.e(0).then(function(){var t=[n("lO7g")];e.apply(null,t)}.bind(this)).catch(n.oe)},meta:{requiresAuth:!0},children:[{path:"/",component:function(e){return n.e(1).then(function(){var t=[n("N87s")];e.apply(null,t)}.bind(this)).catch(n.oe)},meta:{requiresAuth:!0}},{path:"/student",component:function(e){return n.e(2).then(function(){var t=[n("oWYQ")];e.apply(null,t)}.bind(this)).catch(n.oe)},meta:{requiresAuth:!0}}]}]}),p=n("mtWM"),l=n.n(p);a.default.use(o.a),a.default.prototype.$baseUrl="",a.default.config.productionTip=!1,a.default.prototype.$axios=l.a,a.default.prototype.getUrlKey=function(e){return decodeURIComponent((new RegExp("[?|&]"+e+"=([^&;]+?)(&|#|;|$)").exec(location.href)||["",""])[1].replace(/\+/g,"%20"))||null};var d=void 0;l.a.interceptors.request.use(function(e){d=o.a.Loading.service({lock:!0,text:"Loading",spinner:"el-icon-loading",background:"rgba(0, 0, 0, 0.7)"});var t=sessionStorage.getItem("token");return t&&(e.headers.Authorization="Bearer "+t),e.headers.Accept="application/json",e},function(e){return d.close(),Promise.reject(e)}),l.a.interceptors.response.use(function(e){return d.close(),e},function(e){if(d.close(),e.response)switch(e.response.status){case 401:sessionStorage.removeItem("token"),i.replace({path:"/",query:{redirect:i.currentRoute.fullPath}});break;case 403:console.debug(e.response),console.debug("无操作权限");break;case 500:o.a.Message.error(e.response.data)}else o.a.Message.error("网络连接失败，请检查"),console.debug("网络连接失败，请检查",e);return Promise.reject(e)}),new a.default({el:"#app",router:i,components:{App:u},template:"<App/>"})},tvR6:function(e,t){}},["NHnr"]);
//# sourceMappingURL=app.940efd16dc1ca8c8c596.js.map