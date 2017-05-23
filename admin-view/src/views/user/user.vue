<template>
    <panel :inject="injectViews">
        <tool-bar>
            <tool-bar-item  icon="plus" auth="hasPermission('system:user:save')"   rel="user-add" trigger="none">新增</tool-bar-item>
            <tool-bar-item  icon="pencil-square-o" auth="hasPermission('system:user:update')"  rel="user-edit" trigger="single">修改</tool-bar-item>
            <tool-bar-item  icon="trash-o" auth="hasPermission('system:user:delete')"  rel="user-remove" 
              trigger="single" target="confirm" title="是否确定删除该用户？">删除</tool-bar-item>
            <tool-bar-split></tool-bar-split>
            <tool-bar-item  icon="key" auth="hasPermission('system:user:resetPwd')"   rel="user-resetPwd" trigger="single">重置密码</tool-bar-item>
        </tool-bar>
        <search-form label-width="75px" ref="searchForm" :model="searchParams">
            <el-form-item label="用户名">
                <el-input  placeholder="用户名" v-model="searchParams.username"></el-input>
            </el-form-item>
            <el-form-item>
               <el-button type="primary" @click="doSearch">查询</el-button>
             </el-form-item>
          </el-form>
        </search-form>
        <data-grid ref='dataGrid' pageable  url='/admin/user/list'  :searcher="this.$refs.searchForm"
              :respKeyMap="{totalRowsKey:'totalRecords',pageNoKey:'currentPage',datasKey:'rows'}" 
              :defaultSort="{id:'desc'}"
              :paramConverter="paramConverter"
              style="flex:1">
               <grid-column prop="realName" label="真实姓名" width="120"></grid-column>
               <grid-column prop="username" label="用户名" width="150"></grid-column>
               <grid-column prop="outIncludedOrganizationVOs" label="所属机构" :formatter="formatter"></grid-column>
               <grid-column prop="email" label="邮箱"></grid-column>
               <grid-column prop="createTime" label="创建时间" sortable width="180"></grid-column>
               <grid-column prop="lastLoginTime" label="最后登录时间" width="180"></grid-column>
               <grid-column prop="available" label="是否可用" :formatter="formatter" width="90"></grid-column>
        </data-grid>
    </panel>
</template>

<script>
    import userAdd from './user.add.vue'
    import userEdit from './user.edit.vue'
    import userRemove from './user.remove.js'
     import userResetPwd from './user.resetPwd.vue'
    //自定义grid相关的混合类，为了避免全局注入带来的冲突这里局部导入
    import {GridMixins} from '@/mixin'
    
    export default {
        name:"user",
        mixins:[GridMixins],
        data(){
            return {
                 injectViews:{
                    'user-add':userAdd,
                    'user-edit':userEdit,
                    'user-remove':userRemove,
                    'user-resetPwd':userResetPwd
                  },
                  searchParams:{
                      username:''
                  }
            };
        },
        methods:{
            doSearch:function(){
                this.$refs["dataGrid"].loadData();
            },
             formatter(row, column) {
                  let val = row[column["property"]];
                 if(column["property"]=="available"){
                     return val?'是':'否';
                 }else if(column["property"]=="outIncludedOrganizationVOs"){
                    if(val && val.length > 0){
                        let names="";
                         for(let i=0;i<val.length;i++){
                                     names+=val[i].name;
                                     if(i < val.length - 1){
                                         names+=",";
                                     }
                         }
                         return names;
                    }
                    return "";
                 }
                

                 
             }
        }
    }
</script>