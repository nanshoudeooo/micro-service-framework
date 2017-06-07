<template>
     <panel>
         <h1 class="menu-title">菜单基本信息</h1>
         <el-form ref="myForm"  :inline="true" label-width="100px">
           <div>
              <el-form-item label="上级菜单：" class="menu-form-item">
                <template v-if="menu.parent=='0'">无</template>
                <template v-else>{{menu.parentName}}</template>
              </el-form-item>
                <el-form-item label="菜单名称：" class="menu-form-item">
                {{menu.name}}
              </el-form-item>
               <el-form-item label="是否是目录：" class="menu-form-item">
                 <template v-if="menu.dir">是</template>
                 <template v-else>否</template>
              </el-form-item>
           </div>
          <div>
               <el-form-item label="菜单位置：" class="menu-form-item">
                 <template v-if="menu.menuType==='LEFT'">左侧</template>
                 <template v-if="menu.menuType==='TOP'">顶部</template>
              </el-form-item>
               <el-form-item label="链接地址：" class="menu-form-item">
                 {{menu.url}}
              </el-form-item>
              <el-form-item label="菜单图标：" class="menu-form-item">
                 {{menu.icon}}
              </el-form-item>
          </div>
          <div>
             <el-form-item label="权限标识：" class="menu-form-item">
                 {{menu.permission}}
              </el-form-item>
             <el-form-item label="排序序号：" class="menu-form-item">
                 {{menu.sortNum}}
              </el-form-item>
          </div>
         </el-form>
         <template v-if="!menu.dir">
         <h1 class="menu-title">操作列表信息<el-button type="text" style="margin-left:10px" @click="showActionCreateForm">创建操作信息</el-button></h1>
          <data-grid ref='dataGrid'  url='/admin/resource/listByParentID' 
              :params="{'parentID':param.resourceID}" respKeyMap="none"
              style="flex:1">
               <grid-column prop="name" label="操作名称"></grid-column>
               <grid-column prop="url" label="链接地址"></grid-column>
               <grid-column prop="permission" label="权限标识" :width="250"></grid-column>
               <el-table-column  label="操作" :resizable='false' :width="120">
                   <template scope='scope'>
                       <el-button @click="showActionEditForm(scope.row)" type="text" >修改</el-button>
                       <el-button @click="actionRemove(scope.row)" type="text" >删除</el-button>
                   </template>
              </el-table-column>
          </data-grid>
          </template>
     </panel>
</template>
<style>
    .menu-form-item{
        min-width:250px;
        margin-bottom:0;
    }
    .menu-form-item .el-form-item__content{
        color:#999;
    }
    .menu-title{
        font-size: 16px;
        padding:5px 2px;
        color:#999;
        border-bottom:1px dotted #E2E2E2;
        margin:10px 0;
    }
</style>
<script>
    //自定义grid相关的混合类，为了避免全局注入带来的冲突这里局部导入
    import {GridMixins} from '@/mixin'
    import actionForm from './action.form.vue'

    export default {
        name:'menu-detail',
        mixins:[GridMixins],
        data(){
            return {
                 menu:{
                     id:'',
                     icon:'',
                     menuType:'LEFT',
                     name:'',
                     permission:'',
                     resourceType:'MENU',
                     sortNum:0,
                     url:'',
                     dir:true,
                     parentName:'',
                     parent:'0'
                 },
                param:{
                    resourceID:''
                }
            };
        },
        watch:{
            param:{
               deep: true,
               handler:function(param){
                    let self = this;
                self.$$Http.post.call(self,'/admin/resource/getByID',{resourceID:param.resourceID})
                .then(function(content){
                    self.$$Func.fillValue(content,self.menu);
                    if(!self.menu.icon) self.menu.icon ="无"; 
                    if(self.menu.parent=='0') self.menu.parentName = "无";
                    if(self.$refs.dataGrid){
                        //重新刷新grid
                        self.$refs.dataGrid.loadData();
                    }
                },function(resp){
                    self.$alert(resp.message, '提示', {confirmButtonText: '确定'});	
                });
               }
            }
        },
        methods:{
            actionRemove:function(row){
                let _ = this;
                _.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                   _.$$Http.post.call(_,'/admin/resource/delete',{resourceID:row.id})
                   .then(function(){
                        _.$refs.dataGrid.loadData();
                   },function(resp){
                        if(!resp.treated)
                           _.$alert(resp.message, '提示', {confirmButtonText: '确定'});
                   });
                }).catch(function(){});
            },
            showActionCreateForm:function(){
                let _ = this;
                this.$$Dialog.open({
                     title:"创建菜单操作信息",
                     content:actionForm,
                     width:"500px",
                     height:"400px",
                     param:{parentName:_.menu.name,parent:_.menu.id},
                     okCallback:function(component,close){
                       component.submitForm().then(function(){
                          _.$refs.dataGrid.loadData();
                           close();
                       },function(resp){
                          if(!resp.treated)
                           _.$alert(resp.message, '提示', {confirmButtonText: '确定'});
                       });
                       return false;
                     }
               });
            },
            showActionEditForm:function(row){
                 let _ = this;
                this.$$Dialog.open({
                     title:"修改菜单操作信息",
                     content:actionForm,
                     width:"500px",
                     height:"400px",
                     param:{id:row.id},
                     okCallback:function(component,close){
                       component.submitForm().then(function(){
                          _.$refs.dataGrid.loadData();
                           close();
                       },function(resp){
                          if(!resp.treated)
                           _.$alert(resp.message, '提示', {confirmButtonText: '确定'});
                       });
                       return false;
                     }
               });
            }
        }
    }
</script>