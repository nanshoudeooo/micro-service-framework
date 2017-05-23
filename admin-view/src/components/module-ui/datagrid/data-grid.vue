<template>
    <div class="data-grid">
       <el-table ref='et' border :data="dataSet" :highlight-current-row="!checkbox" height="100" style="flex:1" 
                 @selection-change='onSelectChange'
                 @row-click='onRowClick'
                 @sort-change='onSortChange'>
           <el-table-column type="index" width="30" label=" " class-name="recNo" align="center" fixed :resizable='false'></el-table-column>
           <el-table-column type="selection" width="45" v-if="checkbox"   fixed :resizable='false' :selectable="selectable"></el-table-column>
           <slot></slot>
       </el-table>
       <div class="page-bar" v-if="pageable">
            <el-pagination
               @size-change="handleSizeChange"
               @current-change="handleCurrentChange"
               :current-page="pageNo"
               :page-sizes="[15, 20, 30]"
               :page-size="pageSize"
               layout="total, sizes, prev, pager, next, jumper"
               :total="totalRows">
             </el-pagination>
       </div>
       <div class="grid-loading" style="position:absolute;width:100%;height:100%;z-indx:10" v-if="loading">
          <div style="position:relative;left:50%;top:50%;transform: translate(-50%,-50%);border:1px solid #50bfff;width:100px;text-align:center;height:28px;line-height:28px;background:#fff;font-size:12px">
             <i class="el-icon-loading"></i>&nbsp;&nbsp;数据加载中
         </div>
       </div>
    </div>
</template>
<style>
     .data-grid{
          width:99.8%;
          margin:0 auto;
          background: #fff;
          display:flex;
          flex-direction:column;
          overflow:hidden;
          border:1px solid #ccc;
          position:relative;
     }
    .data-grid th{
   background-color: #efefef;
    background: -webkit-linear-gradient(top,#F9F9F9 0,#efefef 100%);
    background: -moz-linear-gradient(top,#F9F9F9 0,#efefef 100%);
    background: -o-linear-gradient(top,#F9F9F9 0,#efefef 100%);
    background: linear-gradient(to bottom,#F9F9F9 0,#efefef 100%);
    background-repeat: repeat-x;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#F9F9F9,endColorstr=#efefef,GradientType=0);
}
.data-grid .el-table{
    border:none;
}
.data-grid .el-table__body-wrapper{
    overflow-x:hidden;
}
.data-grid .el-table--border td{
    border-style:dotted;
}
.data-grid .el-table--border th{
    border-style:solid;
}
.data-grid .el-table td, .data-grid .el-table th{height: 30px;}
 .data-grid th .cell{
    background: none;
    font-size:12px;
    color:#8297a2
}
.data-grid td.recNo{
    background-color: #efefef;
    background: -webkit-linear-gradient(top,#F9F9F9 0,#efefef 100%);
    background: -moz-linear-gradient(top,#F9F9F9 0,#efefef 100%);
    background: -o-linear-gradient(top,#F9F9F9 0,#efefef 100%);
    background: linear-gradient(to bottom,#F9F9F9 0,#efefef 100%);
    background-repeat: repeat-x;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#F9F9F9,endColorstr=#efefef,GradientType=0);
}
.data-grid .recNo .cell{
   word-break:normal;
   padding:0;
}
.data-grid .el-table__fixed, .el-table__fixed-right {
    border-right: none;
     border-bottom: none;
    box-shadow: none; 
}
.el-table__fixed-right::before, .el-table__fixed::before{
    background:none;
}
.data-grid .page-bar{
   text-align:left;
   background:#F4F4F4;
   height:35px;
   padding-top:3px;
   border:1px solid #dfe6ec;
   border-top:none;
    border-bottom:none;
   z-index:4;
   box-shadow:0 -1px 8px #d3d4d6;
}
</style>
<script>
   import {$$Http} from '@/util';
    export default {
        name:'data-grid',
        componentName:'data-grid',
        data(){
            return {
                dataSet:[],
                totalRows:(this.data ? this.data.length : 0),
                pageNo:1,
                pageSize:15,
                selectedRows:[],
                sort:Object.assign({},this.defaultSort),
                loading:false
            }
        },
        props:{
            selectable: Function,
            checkbox:Boolean,//是否多选
            pageable:Boolean,//是否可以分页
            url:String,//请求地址
            data:Array,//数据集不可与url同用
            params:Object,//当没有查询区域时又有初始化条件，可以用查询参数
            searcher:Object,//查询区域
            //如果为none时，代表不需要该设置
            respKeyMap:{
                type:[Object,String],
                default:function(){
                    return {totalRowsKey:'totalRecords',pageNoKey:'pageNo',datasKey:'rows'};
                }
            },
            /**
             * 如 {id:'asc',name:'desc'}
             */
            defaultSort:Object,
            //查询参数
            paramConverter:{
                type:Function,
                default:function(){
                   return {};
                }
            },
            //延时加载
            delayLoad:Boolean
        },
        mounted:function(){
            //不是延时加载则grid初始化过后就加载数据了
            if(!this.delayLoad)
                   this.loadData();
        },
        methods:{
            getSelectedRows:function(){
              return this.selectedRows.slice(0,this.selectedRows.length);
            },
            onSelectChange:function(selection){
                this.selectedRows = selection.slice(0,selection.length);
            },
            onRowClick:function(row){
                if(!this.checkbox){
                    this.selectedRows = Array.of(row);
                }
            },
            onSortChange:function(column){
                if(column.prop && column.order){
                   this.sort = {[column.prop] : (column.order=='ascending' ? 'asc' : 'desc')};
                   this.loadData();
                }
            },
            handleSizeChange:function(val){
                this.pageSize = val;
                this.loadData();
            },
            handleCurrentChange:function(val){
               this.pageNo = val;
                this.loadData();
            },
            loadData:function(){
                if(this.data){
                    this.loadLocalData();
                }else{
                    this.loadRemoteData();
                }
            },
            loadLocalData:function(){
              if(!this.pageable){
                  this.dataSet = this.data;
              }else{
                  let startIndex = (this.pageNo - 1) * this.pageSize;
                  let endIndex = this.pageNo * this.pageSize - 1;
                  if(endIndex > this.data.length -1){
                      endIndex = this.data.length -1;
                  }
                   this.dataSet = this.data.slice(startIndex,endIndex+1);
              }
            },
            loadRemoteData:function(){
                 var _ = this,
                   //如果参数转换器没有自定义那么使用默认的
                    searchParams = _.getSearchParams(),
                    convertedParam;
               
                 if(_.pageable){
                     convertedParam = _.paramConverter({pageNo:_.pageNo,pageSize:_.pageSize,sort:_.sort}); 
                 }else{
                     convertedParam = _.paramConverter({sort:_.sort}); 
                 }

                let requestParam =  Object.assign({},searchParams,convertedParam);
                _.loading = true;
                  _.$$Http.post.call(_,_.url,requestParam).then(function(content){
                      if(_.respKeyMap=="none"){
                         _.dataSet = content;    
                      }else{
                         _.dataSet = content[_.respKeyMap.datasKey];
                         if(_.pageable){
                            _.totalRows = content[_.respKeyMap.totalRowsKey];
                            _.pageNo = content[_.respKeyMap.pageNoKey];
                         }
                      }
                      _.loading = false;
                  });
            },
            //获取查询区域的参数
            getSearchParams:function(){
                //如果存在查询区域，则已查询区域的参数为准
                if(this.searcher){
                    return this.searcher.model;
                }else{
                    return this.params || {};
                }
            }
        },
        watch:{
            selectedRows:function(v){
                //如果grid的父组件是panel的话
                if(v.length > 0 && this.$parent.$options.componentName === 'panel'){
                    //查找toolbar组件
                    let toolbar = this.$parent.$children.find(function(child){
                         return child.$options.componentName === 'tool-bar';
                    });
                    //如果存在toolbar组件
                    if(toolbar){
                        //遍历toolbar的子组件tool-bar-item
                        for(let child of toolbar.$children){
                            if(child.$options.componentName==='tool-bar-item'){
                                   //获取panel里注册的组件与当前按钮关联项，这里是组件的js对象而不是实例
                                let rel = this.$parent.inject[child.rel];
                                 //判断当前按钮是否能够使用
                                if(rel && rel.methods && rel.methods.renderToolButton!=undefined){
                                    let available = rel.methods.renderToolButton(v);
                                    child.available = available;
                                }else if(rel && rel.renderToolButton != undefined){//不是vue组件，纯js文件对象
                                     let available = rel.renderToolButton(v);
                                    child.available = available;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
</script>