<template>
    <panel :inject="injectViews" ref="panel">
        <tool-bar>
            <tool-bar-item  icon="plus" auth="hasPermission('system:resource:save')"   rel="menu-add" trigger="none">新增</tool-bar-item>
            <tool-bar-item  icon="pencil-square-o" auth="hasPermission('system:resource:update')"  rel="menu-edit" 
               trigger="none" target="custom" @action="editForm">修改</tool-bar-item>
            <tool-bar-item  icon="trash-o" auth="hasPermission('system:resource:delete')"  rel="menu-remove" 
              trigger="none" target="custom" title="是否确定删除该菜单？" @action="removeMenu">删除</tool-bar-item>
        </tool-bar>
        <div class="content">
            <div class="left">
               <el-tree :data="menus" :props="dataMapProp" @node-click="onClickNode" highlight-current 
               :expand-on-click-node="true" :default-expanded-keys="expandedNodeKeys" auto-expand-parent node-key="id">
               </el-tree>
            </div>
            <div class="right">
                <template v-if="currentMenuId==''">
                   <div class="init-menu-display">选择左侧菜单</div>
                </template>
               <menu-detail ref="menudetail" v-else></menu-detail>
            </div>
        </div> 
    </panel>
</template>
<style scoped>
    .content{
        flex:1;
        display:flex;
        overflow:hidden;
        justify-content:space-between;
    }
    .left,.right{ 
        height:99.3%;
        border:1px solid #ccc;  
        margin-top:1px;
    }
    .left{
        width:280px;
    }
    .right{
        flex:0.999;
        position:relative;
    }
    div.el-tree-node.is-current{
        background:#e4e8f1
    }
    .init-menu-display{
       position:absolute;
       width:150px;
       height:80px;
       left:50%;
       top:50%; 
       transform:translate(-50%,-50%);
       font-weight:bolder;
       color:#CBD4DC
    }
</style>
<script>
   import menuDetail from './menu.detail.vue'
   import menuAdd from './menu.add.vue'
   import menuEdit from './menu.edit.vue'
   import formateMenu from './menu.formate.js';

    export default {
       name:'resource',
       components:{
             "menu-detail":menuDetail
       },
       data(){
           return {
               injectViews:{
                    'menu-add':menuAdd,
                    'menu-edit':menuEdit
               },
               menus:[],
              dataMapProp:{
                  children: 'children',
                  label: 'name'
              },
              currentMenuId:'',
              expandedNodeKeys:[]
           };
       },
      mounted(){
         this.loadTree();
         //监听panel的reload事件
         this.$refs.panel.$on("reload",this.loadTree);
      },
      beforeDestroy(){
          //销毁panel的reload监听事件
         this.$refs.panel.$off("reload",this.loadTree);
      },
      methods:{
          loadTree:function(){
                let self = this;
                self.$$Http.post.call(self,'/admin/resource/listByResourceTypeAndDir',{resourceType:'MENU'})
                .then(function(content){
                    self.menus = formateMenu(content);
                    self.expandedNodeKeys = [];
                    if(self.menus && self.menus.length > 0){
                        self.expandedNodeKeys.push(self.menus[0].id);
                    }
                },function(resp){
                    if(!resp.treated)
                      self.$alert(resp.message, '提示', {confirmButtonText: '确定'});	
                });
          },
           onClickNode:function(data,node){
               this.currentMenuId = data.id;
               this.$nextTick(function(){
                   let inst = this.$refs.menudetail;
                  inst.$set(inst.param,"resourceID",data.id);
               });
               
           },
           editForm:function(rel){
                if(!this.currentMenuId){
                     this.$message({
                              showClose: true,
                               message: '请选择需要操作的记录',
                               type: 'warning'
                     });
                }else{
                     this.$refs.panel.key='menu-edit';
                     let _ = this;
                     //改了panel内部的数据，需要等待响应结束后再去获取他的子组件
                     this.$refs.panel.$nextTick(function(){
                            this.$refs.currentView.$options["rowdatas"] = [{id:_.currentMenuId}];
                     })
                    
                }
           },
           removeMenu:function(){
                if(!this.currentMenuId){
                     this.$message({
                              showClose: true,
                               message: '请选择需要操作的记录',
                               type: 'warning'
                     });
                }else{
                    let _ = this;
                    _.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function(){
                    _.$$Http.post.call(_,'/admin/resource/delete',{resourceID:_.currentMenuId})
                    .then(function(){
                             _.$refs.panel.reload();
                    },function(resp){
                            if(!resp.treated)
                            _.$alert(resp.message, '提示', {confirmButtonText: '确定'});
                    });
                    }).catch(function(){});
                }
           }
       }
    };
</script>