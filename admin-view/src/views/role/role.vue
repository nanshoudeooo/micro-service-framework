<template>
    <panel :inject="injectViews">
        <tool-bar>
            <tool-bar-item  icon="plus" auth="hasPermission('system:role:save')"   rel="role-add" trigger="none">新增</tool-bar-item>
            <tool-bar-item  icon="pencil-square-o" auth="hasPermission('system:role:update')"  rel="role-edit" trigger="single">修改</tool-bar-item>
            <tool-bar-item  icon="trash-o" auth="hasPermission('system:role:delete')"  rel="role-remove" 
              trigger="single" target="confirm" title="是否确定删除该角色？">删除</tool-bar-item>
        </tool-bar>
        <search-form label-width="75px" ref="searchForm" :model="searchParams">
            <el-form-item label="角色名">
                <el-input  placeholder="角色名" v-model="searchParams.rolename"></el-input>
            </el-form-item>
            <el-form-item>
               <el-button type="primary" @click="doSearch">查询</el-button>
             </el-form-item>
          </el-form>
        </search-form>
        <data-grid ref='dataGrid' pageable  url='/admin/role/list'  :searcher="this.$refs.searchForm"
              :respKeyMap="{totalRowsKey:'totalRecords',pageNoKey:'currentPage',datasKey:'rows'}" 
              :defaultSort="{createTime:'asc'}"
              :paramConverter="paramConverter"
              style="flex:1">
              <grid-column prop="rolename" label="角色名"></grid-column>
               <grid-column prop="description" label="描述"></grid-column>
               
        </data-grid>
    </panel>
</template>

<script>
    import roleAdd from './role.add.vue'
    import roleEdit from './role.edit.vue'
    import roleRemove from './role.remove.js'
    //自定义grid相关的混合类，为了避免全局注入带来的冲突这里局部导入
    import {GridMixins} from '@/mixin'
    
    export default {
        name:"role",
        mixins:[GridMixins],
        data(){
            return {
                 injectViews:{
                    'role-add':roleAdd,
                    'role-edit':roleEdit,
                    'role-remove':roleRemove,
                  },
                  searchParams:{
                      rolename:''
                  }
            };
        },
        methods:{
            doSearch:function(){
                this.$refs["dataGrid"].loadData();
            }
        }
    }
</script>