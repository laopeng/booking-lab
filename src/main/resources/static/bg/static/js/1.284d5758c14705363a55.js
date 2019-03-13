webpackJsonp([1],{N87s:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"table"},[a("div",{staticClass:"crumbs"},[a("el-breadcrumb",{attrs:{"separator-class":"el-icon-arrow-right"}},[a("el-breadcrumb-item",[a("i",{staticClass:"el-icon-setting"}),t._v("预约审核")])],1)],1),t._v(" "),a("el-row",{attrs:{gutter:50}},[a("el-col",{staticClass:"handle-box",attrs:{span:4}},[a("el-select",{attrs:{clearable:"",placeholder:"请选择实验室"},on:{change:function(e){return t.search()}},model:{value:t.params.labId,callback:function(e){t.$set(t.params,"labId",e)},expression:"params.labId"}},t._l(t.labList,function(t){return a("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})}),1)],1),t._v(" "),a("el-col",{staticClass:"handle-box",attrs:{span:4}},[a("el-button",{attrs:{type:"primary",icon:"search"},on:{click:function(e){return t.search()}}},[t._v("搜 索")]),t._v(" "),a("el-button",{attrs:{type:"primary",icon:"close"},on:{click:function(e){return t.reset()}}},[t._v("重 置")])],1)],1),t._v(" "),a("el-table",{ref:"multipleTable",staticStyle:{width:"100%"},attrs:{data:t.tableData,border:""},on:{"selection-change":t.handleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"50"}}),t._v(" "),a("el-table-column",{attrs:{prop:"id.lab.id",label:"编号",width:"210"}}),t._v(" "),a("el-table-column",{attrs:{prop:"id.lab.name",label:"实验室名称",sortable:"",width:"130"}}),t._v(" "),a("el-table-column",{attrs:{prop:"id.bookingDate",label:"日期"}}),t._v(" "),a("el-table-column",{attrs:{prop:"id.bookingTimeRang",label:"时间段"}}),t._v(" "),a("el-table-column",{attrs:{prop:"status",label:"是否可预约"}}),t._v(" "),a("el-table-column",{attrs:{label:"预约学生"},scopedSlots:t._u([{key:"default",fn:function(e){return e.row.student?[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v("学号: "+t._s(e.row.student.number))]),t._v(" "),a("p",[t._v("专业: "+t._s(e.row.student.subject))]),t._v(" "),a("p",[t._v("姓名: "+t._s(e.row.student.name))]),t._v(" "),a("p",[t._v("电话: "+t._s(e.row.student.phone))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v(t._s(e.row.student.name))])],1)])]:void 0}}],null,!0)}),t._v(" "),a("el-table-column",{attrs:{label:"指导教师"},scopedSlots:t._u([{key:"default",fn:function(e){return e.row.teacher?[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v("姓名: "+t._s(e.row.teacher.name))]),t._v(" "),a("p",[t._v("邮箱: "+t._s(e.row.teacher.email))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v(t._s(e.row.teacher.name))])],1)])]:void 0}}],null,!0)}),t._v(" "),a("el-table-column",{attrs:{prop:"audit",label:"审核状态"}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",width:"280"},scopedSlots:t._u([{key:"default",fn:function(e){return[0!==e.row.labStatusLogs.length?a("el-popover",{attrs:{trigger:"hover",placement:"top",width:"600"}},[t._l(e.row.labStatusLogs,function(e){return a("p",{key:e.id},[t._v(t._s(e.content))])}),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v(" 审核日志 ")])],1)],2):t._e(),t._v(" "),"不可用"===e.row.status&&"未审"===e.row.audit?[a("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(a){return t.handleEdit(e.$index,e.row,"通过")}}},[t._v("通过\n          ")]),t._v(" "),a("el-button",{attrs:{size:"small",type:"danger"},on:{click:function(a){return t.handleEdit(e.$index,e.row,"不通过")}}},[t._v("不通过\n        ")])]:t._e()]}}])})],1),t._v(" "),a("div",{staticClass:"pagination"},[a("el-pagination",{attrs:{layout:"total,sizes,prev,pager,next,jumper",total:t.total,"page-size":t.params.size,"current-page":t.params.page+1},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}})],1)],1)},staticRenderFns:[]};var r=a("VU/8")({data:function(){return{url:this.$baseUrl+"/lab/status/all?sort=idBookingDate,desc&sort=audit&sort=idBookingTimeRang",labUrl:this.$baseUrl+"/labs",auditUrl:this.$baseUrl+"/lab/status/audit",tableData:[],total:0,params:{page:0,size:20,labId:null},labList:[]}},created:function(){this.getLabs(),this.search()},methods:{getLabs:function(){var t=this;this.$axios.get(this.labUrl).then(function(e){t.labList=e.data})},search:function(){var t=this;this.$axios.get(this.url,{params:this.params}).then(function(e){t.tableData=e.data.content,t.total=e.data.totalElements})},reset:function(){this.params.labId=null,this.search()},handleSizeChange:function(t){this.params.size=t,this.search()},handleCurrentChange:function(t){this.params.page=t-1,this.search()},handleEdit:function(t,e,a){var n=this;this.$confirm("此操作将"+a+"学生的申请, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){e.audit=a,n.$axios.put(n.auditUrl,e).then(function(t){n.search()}).catch(function(){e.audit="未审"})})},handleSelectionChange:function(t){}}},n,!1,function(t){a("d2m8")},"data-v-4597ae43",null);e.default=r.exports},d2m8:function(t,e){}});
//# sourceMappingURL=1.284d5758c14705363a55.js.map