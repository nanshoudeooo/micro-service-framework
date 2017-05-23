<template>
    <panel style="flex-direction:row">
       <div class="unauthorizedPart">
          <div class="title-bar">
            <label>未授权用户</label>
            <span class="spliter"></span>
            <button @click="allotRoleForUser">分配角色用户<icon name="long-arrow-right" style="vertical-align:middle;margin-bottom:3px;color:#B0B0B0;margin-left:5px;"></icon></button>
          </div>
          <div class="filter-bar">
             <input class="input" placeholder="用户名/真实姓名" 
                @keyup.enter="doFilter('unAuthGridFilterText',$event.target.value)"/>
             <icon class="inputIco"name="search"></icon>    
          </div>
          <data-grid ref='dataGridUnAuthed'  url="/admin/role/listUnauthorizedUsers" delayLoad
            respKeyMap="none" checkbox 
            :params="{roleID:param.role.id,organizationID:param.orgId,words:param.inputFilter.unAuthGridFilterText}"
              style="flex:1;width:96%;margin:5px auto">
               <grid-column prop="username" label="用户名"></grid-column>
               <grid-column prop="realName" label="真实姓名"></grid-column>
          </data-grid>
       </div>
       <div class="authorizedPart">
            <div class="title-bar">
            <label>已授权用户</label>
            <span class="spliter"></span>
             <button @click="removeRoleForUser"><icon name="long-arrow-left" style="vertical-align:middle;margin-bottom:3px;color:#B0B0B0;margin-right:5px;"></icon>删除角色用户</button>
          </div>
            <div class="filter-bar">
                <input class="input" placeholder="用户名/真实姓名"
                   @keyup.enter="doFilter('authedGridFilterText',$event.target.value)"></input>
                <icon class="inputIco"name="search"></icon>    
          </div>
           <data-grid ref='dataGridAuthed'  url="/admin/role/listAuthorizedUsers" delayLoad
            respKeyMap="none" checkbox  :selectable="selectable"
             :params="{roleID:param.role.id,organizationID:param.orgId,words:param.inputFilter.authedGridFilterText}"
              style="flex:1;width:96%;margin:5px auto">
               <grid-column prop="username" label="用户名"></grid-column>
               <grid-column prop="realName" label="真实姓名"></grid-column>
          </data-grid>
       </div>
        <div class="filterPart">
          <div class="title-bar">
            <label>机构筛选用户</label>
            <span class="spliter"></span>
             <button @click="clearOrgSelected"><icon name="refresh" style="vertical-align:middle;margin-bottom:3px;color:#B0B0B0;margin-right:5px;"></icon>清除选中</button>
          </div>
          <div class="filter-bar">
             <input class="input" placeholder="机构名称" v-model="filterOrgName"/><icon class="inputIco"name="search"></icon>    
          </div>
            <el-tree ref="orgList" :data="orgList" :props="dataMapProp" @node-click="onClickNode" :highlight-current="treeNodeSelectedHighlight" style="margin-top:5px;"
               :expand-on-click-node="false" :default-expanded-keys="expandedNodeKeys" 
                auto-expand-parent node-key="id"   :filter-node-method="filterNode">
           </el-tree>
       </div>
    </panel>
</template>
<style scoped>
    .filterPart{
       width:250px;
       border:1px solid #ccc;
    }
    .unauthorizedPart,.authorizedPart{
        flex:1;
        border:1px solid #ccc;
        overflow:hidden;
        display:flex;
        flex-direction:column;
    }
    .unauthorizedPart,.authorizedPart{
      border-right:none;
    }
    .operPart{
        width:100px;
    }
    .title-bar{
        height:35px;
        line-height:32px;
        border-bottom:1px solid #ccc;
        background:#fafafa
    }
    .title-bar label{
        margin-left:5px;
        font-weight:bold;
        font-size:12px;
        vertical-align:middle;
    }
   .title-bar .spliter{
        height: 25px;
        margin-left: 10px;
        margin-right: 10px;
        display: inline-block;
        border-left: 1px solid #ccc;
        border-right: 1px solid #fff;
        vertical-align:middle;
    }
    .filter-bar{
        width:96%;
        margin:0 auto;
        height:45px;
        border-bottom:1px solid #DADADA;
        position:relative;
    }
    .filter-bar .input{
       width:100%;
       position:absolute;
       border:none;
       top:10px;
       height:25px;
       outline:none;
      
    }
     .filter-bar .inputIco{
         position:absolute;
         right:0;
         top:15px;
          color:#DFE1E3;
     }
</style>
<script>
    import formateOrg from '@/views/organization/organization.formate.js';
    
    export default{
        name:'role-member',
        data(){
            return {
                 orgList:[],
                 param:{
                    orgId:'',
                    role:{},
                    inputFilter:{
                       unAuthGridFilterText:'',
                        authedGridFilterText:''
                    }
                },
                dataMapProp:{
                     children: 'children',
                     label: 'name'
                },
                expandedNodeKeys:[],
                filterOrgName:'',
                treeNodeSelectedHighlight:false
            };
        },
        mounted(){
           //加载机构数据
           let self = this;
           self.$$Http.post.call(self,'/admin/organization/list')
               .then(function(content){
                   self.orgList = formateOrg(content);
                    self.expandedNodeKeys = [];
                    if(self.orgList && self.orgList.length > 0){
                        self.expandedNodeKeys.push(self.orgList[0].id);
                    }
               },function(resp){//响应失败
                     if(!resp.treated)
                        self.$$MessageBox.alert(resp.message, '提示', {confirmButtonText: '确定'});	
                });
        },
         watch:{
            filterOrgName(val){
                this.$refs.orgList.filter(val);
            },
            param:{
               deep: true,
               handler:function(val){
                   //一定要加这个nextTick，不然数据改了grid还没有渲染
                  this.$nextTick(function(){
                        this.$refs.dataGridUnAuthed.loadData();
                        this.$refs.dataGridAuthed.loadData();
                  })
               }
            }
        },
        methods:{
           onClickNode:function(data,node){
               this.treeNodeSelectedHighlight = true;
               this.param.orgId = data.id;
           },
           filterNode:function(value, data) {
               if (!value) return true;
             return data.name.indexOf(value) !== -1;
           },
           allotRoleForUser:function(){
                let rows = this.$refs.dataGridUnAuthed.getSelectedRows();
                if(!rows || rows.length==0){
                     //提示选择一条记录
                      this.$message({
                              showClose: true,
                               message: '请选择需要分配的用户记录',
                               type: 'warning'
                            });
                }else{
                    let _ = this;
                     _.$confirm('确认将角色【'+_.param.role.description+'】分配给当前选中的用户吗？', '提示', 
					      {
                            confirmButtonText: '确认',
					        cancelButtonText: '取消',
                            type: 'warning'
                          }).then(function(){
                              let userIds = '';
                              for(let row of rows){
                                    userIds+=','+row.id;
                              }
                              userIds = userIds.substring(1);
                              _.$$Http.post.call(_,'/admin/userRoleRelation/save',{roleID:_.param.role.id,userIDs:userIds})
                                 .then(function(content){
                                        _.$message({showClose: true,message: '操作成功', type: 'info'});
                                        _.$refs.dataGridUnAuthed.loadData();
                                        _.$refs.dataGridAuthed.loadData();
                                 },function(resp){
                                       if(!resp.treated)
                                            _.$$MessageBox.alert(resp.message, '提示', {confirmButtonText: '确定'});	
                                 });
				          }).catch(function(e){});
                }
           },
           removeRoleForUser:function(){
                let rows = this.$refs.dataGridAuthed.getSelectedRows();
                if(!rows || rows.length==0){
                     //提示选择一条记录
                    this.$message({ showClose: true,message: '请选择需要移除角色的的用户记录', type: 'warning'});
                }else{
                    let _ = this;
                     _.$confirm('确认解除角色【'+_.param.role.description+'】与当前选中的用户的关系？', '提示', 
					      {
                            confirmButtonText: '确认',
					        cancelButtonText: '取消',
                            type: 'warning'
                          }).then(function(){
                              let userIds = '';
                              for(let row of rows){
                                    userIds+=','+row.id;
                              }
                              userIds = userIds.substring(1);
                              _.$$Http.post.call(_,'/admin/userRoleRelation/delete',{roleID:_.param.role.id,userIDs:userIds})
                                 .then(function(content){
                                        _.$message({showClose: true,message: '操作成功', type: 'info'});
                                        _.$refs.dataGridUnAuthed.loadData();
                                        _.$refs.dataGridAuthed.loadData();
                                 },function(resp){
                                       if(!resp.treated)
                                            _.$$MessageBox.alert(resp.message, '提示', {confirmButtonText: '确定'});	
                                 });
				          }).catch(function(e){});
                }
           },
           clearOrgSelected:function(){
              this.treeNodeSelectedHighlight = false;
                this.param.orgId = '';
           },
           doFilter:function(key,val){
               this.param.inputFilter[key] = val;
           },
           selectable:function(row){
               //如果当前用户是内置的，而且当前角色是管理员
                if(row.builtIn && this.param.role.rolename=="admin"){
                    return false;
                }
                 return true;
           }
        }
    }
</script>