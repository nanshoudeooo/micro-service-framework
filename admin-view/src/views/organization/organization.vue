<template>
    <panel :inject="injectViews" ref="panel">
        <tool-bar>
            <tool-bar-item  icon="plus" auth="hasPermission('system:organization:save')"   rel="org-add" trigger="none">新增</tool-bar-item>
            <tool-bar-item  icon="pencil-square-o" auth="hasPermission('system:organization:update')"  rel="org-edit" 
               trigger="none" target="custom" @action="editForm">修改</tool-bar-item>
            <tool-bar-item  icon="trash-o" auth="hasPermission('system:organization:delete')"  rel="org-remove" ref="removeBtn"
              trigger="none" target="custom" title="是否确定删除该机构？" @action="removeOrg">删除</tool-bar-item>
        </tool-bar>
        <div class="content">
            <div class="left">
               <el-tree :data="orgs" :props="dataMapProp" @node-click="onClickNode" highlight-current 
               :expand-on-click-node="true" :default-expanded-keys="expandedNodeKeys" auto-expand-parent node-key="id">
               </el-tree>
            </div>
            <div class="right">
                <template v-if="currentOrgId==''">
                   <div class="init-org-display">选择左侧组织机构</div>
                </template>
               <org-detail ref="orgdetail" v-else></org-detail>
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
    .init-org-display{
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
   import orgDetail from './org.detail.vue'
   import orgAdd from './org.add.vue'
   import orgEdit from './org.edit.vue'
   import formateOrg from './organization.formate.js';

    export default {
       name:'organization',
       components:{
             "org-detail":orgDetail
       },
       data(){
           return {
               injectViews:{
                    'org-add':orgAdd,
                    'org-edit':orgEdit
               },
               orgs:[],
              dataMapProp:{
                  children: 'children',
                  label: 'name'
              },
              currentOrgId:'',
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
                self.$$Http.post.call(self,'/admin/organization/list')
                .then(function(content){
                    self.orgs = formateOrg(content);
                    self.expandedNodeKeys = [];
                    if(self.orgs && self.orgs.length > 0){
                        self.expandedNodeKeys.push(self.orgs[0].id);
                    }
                },function(resp){
                    if(!resp.treated)
                      self.$alert(resp.message, '提示', {confirmButtonText: '确定'});	
                });
          },
           onClickNode:function(data,node){
               this.currentOrgId = data.id;
               this.$nextTick(function(){
                   let inst = this.$refs.orgdetail;
                  inst.$set(inst.param,"organizationID",data.id);
                  if(data.builtIn){
                       this.$refs.removeBtn.$set(this.$refs.removeBtn,"available",false);
                  }else{
                      this.$refs.removeBtn.$set(this.$refs.removeBtn,"available",true);
                  }
                  
               });
               
           },
           editForm:function(rel){
                if(!this.currentOrgId){
                     this.$message({
                              showClose: true,
                               message: '请选择需要操作的记录',
                               type: 'warning'
                     });
                }else{
                     this.$refs.panel.key='org-edit';
                     let _ = this;
                     //改了panel内部的数据，需要等待响应结束后再去获取他的子组件
                     this.$refs.panel.$nextTick(function(){
                            this.$refs.currentView.$options["rowdatas"] = [{id:_.currentOrgId}];
                     })
                    
                }
           },
           removeOrg:function(){
                if(!this.currentOrgId){
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
                    _.$$Http.post.call(_,'/admin/organization/delete',{id:_.currentOrgId})
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