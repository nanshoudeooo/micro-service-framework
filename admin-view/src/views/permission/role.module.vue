<template>
    <panel style="flex-direction:row">
       <div class="modulePart">
         <div class="title-bar">
            <label>模块授权列表</label>
            <span class="spliter"></span>
             <button @click="allotRoleForResource"><icon name="cogs" style="vertical-align:middle;margin-bottom:3px;color:#B0B0B0;margin-right:5px;"></icon>批量授权</button>
          </div>
          <div class="module-list">
            <el-row class="bd-bottom shadow">
                <el-col :span="1" class="col bd-right bolder"><el-checkbox :indeterminate="isIndeterminate" @change="checkAll"></el-checkbox></el-col>
                <el-col :span="4"  class="col bd-right bolder">模块名称</el-col>
                <el-col :span="19"  class="col bolder" style="padding-left:15px;">操作项</el-col>
            </el-row>
            <div class="table-body">
             <el-checkbox-group  v-model="checkedResIds">
              <!--分组开始-->
               <template v-for="(item,idx) in dataSet">
                    <el-row class="shadow" :class="{'bd-bottom':idx < dataSet.length-1}" v-if="item.dir">
                            <el-col :span="24" class="col" style="background:#EAEAEA">{{item.name}}</el-col>
                    </el-row>
                    <el-row :class="{'bd-bottom':idx < dataSet.length-1 && dataSet[idx+1].dir}" v-else-if="item.id==''">
                        <el-col :span="24" class="col" style="text-align:center">{{item.name}}</el-col>
                    </el-row>
                   <el-row :class="{'bd-bottom':idx < dataSet.length-1 && dataSet[idx+1].dir}" v-else>
                            <el-col :span="1" class="col"><el-checkbox :label="item.id" @change="associate(item.children,$event)" :disabled="item.builtIn && param.role.rolename=='admin'">&nbsp;</el-checkbox></el-col>
                            <el-col :span="4" class="col">{{item.name}}</el-col>
                            <el-col :span="19" class="col">
                            <template v-for="(child,index) in item.children">
                                <el-checkbox :label="child.id" :class="{ml15:index==0}"  @change="associate(item,$event)" :disabled="child.builtIn && param.role.rolename=='admin'">{{child.name}}</el-checkbox>
                            </template>
                            </el-col>
                      </el-row>
                </template>
                <!--分组结束-->
              </el-checkbox-group>
            </div>
          </div>
       </div>
       <div class="filterPart">
          <div class="title-bar">
            <label>模块筛选</label>
            <span class="spliter"></span>
             <button @click="clearMenuSelected"><icon name="refresh" style="vertical-align:middle;margin-bottom:3px;color:#B0B0B0;margin-right:5px;"></icon>清除选中</button>
          </div>
          <div class="filter-bar">
             <input class="input" placeholder="模块名称" v-model="filterModuleName"/><icon class="inputIco"name="search"></icon>    
          </div>
            <el-tree ref="moduleList" :data="moduleList" :props="dataMapProp" @node-click="onClickNode" :highlight-current="treeNodeSelectedHighlight" style="margin-top:5px;"
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
       border-left:none;
    }
    .modulePart{
        flex:1;
        border:1px solid #ccc;
        display:flex;
        flex-direction:column;
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
     .module-list{
       width:98%;
       border:1px solid #ccc;
       margin:5px auto;
       font-size:14px;
       display:flex;
       flex-direction:column;
     }
     .bd-bottom{
         border-bottom:1px solid #ccc;
     }
     .shadow{
          box-shadow:0 1px 3px #ccc;
     }
     .bd-right{
         border-right:1px solid #ccc;
     }
     .col{
         padding-left:5px;
         min-height:30px;
         line-height:30px;
     }
     .bolder{
         font-weight:bolder;
         background:#fafafa
     }
     .table-body{
         flex:1;
         overflow:auto;
     }
     .ml15{margin-left:15px}
</style>
<script>
    import roleModuleResolve from './role.module.resolve.js'
    import formateMenu from '@/views/resource/menu.formate.js';
    export default{
        name:'role-module',
        data(){
            return {
                 //数据源
                 dataSet:[],
                 checkedResIds:[],
                 allCheckboxIds:[],
                 isIndeterminate:true,
                 param:{
                    role:{},
                    menuId:''
                 },
                 filterModuleName:'',
                 moduleList:[],
                 expandedNodeKeys:[],
                 treeNodeSelectedHighlight:false,
                 dataMapProp:{
                     children: 'children',
                     label: 'name'
                }
            };
        },
        mounted(){
            this.$nextTick(function(){
                this.loadRoleModuleData();
                //加载菜单数据
                 this.loadTree();
            })
        },
         watch:{
            filterModuleName(val){
                this.$refs.moduleList.filter(val);
            },
            param:{
               deep: true,
               handler:function(val){
                   //一定要加这个nextTick，不然数据改了还没有渲染
                  this.$nextTick(function(){
                       this.loadRoleModuleData();
                  })
               }
            }
        },
        methods:{
            loadTree:function(){
                let self = this;
                self.$$Http.post.call(self,'/admin/resource/listByResourceTypeAndDir',{resourceType:'MENU'})
                .then(function(content){
                    self.moduleList = formateMenu(content);
                    self.expandedNodeKeys = [];
                    if(self.moduleList && self.moduleList.length > 0){
                        self.expandedNodeKeys.push(self.moduleList[0].id);
                    }
                },function(resp){
                    if(!resp.treated)
                      self.$alert(resp.message, '提示', {confirmButtonText: '确定'});	
                });
          },
            loadRoleModuleData:function(){
                  let self = this;
                self.$$Http.post.call(self,'/admin/resource/listCheckedResourceByRoleIDAndResourceID',{id:self.param.role.id,resourceID:self.param.menuId})
                .then(function(content){
                    let datas = roleModuleResolve(content);
                    self.dataSet = datas.resources;
                    self.checkedResIds =  datas.authedRes;
                    self.allCheckboxIds = datas.allIds;
                    if(!self.checkedResIds ||  self.checkedResIds.length==0){
                        self.isIndeterminate = false;
                    }
                },function(resp){
                        if(!resp.treated)
                            self.$alert(resp.message, '提示', {confirmButtonText: '确定'});	
                });
            },
             onClickNode:function(data,node){
               this.treeNodeSelectedHighlight = true;
               this.param.menuId = data.id;
           },
           filterNode:function(value, data) {
               if (!value) return true;
             return data.name.indexOf(value) !== -1;
           },
            associate:function(items,event){
                let canCancelChecked = true;
               if(Object.prototype.toString.call(items)!='[object Array]') {//当不是数组时，不可以关联取消父
                   items = [items];
                   canCancelChecked = false;
               }

                if(event.target.checked){
                    this.makeChecked(items);
                }else{
                   canCancelChecked && this.cancelChecked(items);
                }
            },
            makeChecked:function(arr){
                if(this.checkedResIds.length==0){
                    this.checkedResIds = this.checkedResIds.concat(arr);
                }else{
                   let tempStr =";" + this.checkedResIds.join(";") + ";";
                   for(let obj of arr){
                       let objId = ";" + obj.id + ";";
                       if(tempStr.indexOf(objId) == -1){
                            this.checkedResIds.push(obj.id);
                       }
                   }
                }
            },
            cancelChecked:function(arr){
                 if(this.checkedResIds.length==0){
                     return;
                 }else{
                      let tempStr =";"+this.checkedResIds.join(";")+";";
                      for(let obj of arr){
                          //不是管理员的内置的才能取消勾选
                          if(!(this.param.role.rolename=="admin" && obj.builtIn)){
                                tempStr = tempStr.replace(";"+obj.id+";",";");
                          }
                          
                      }
                      tempStr = tempStr.substring(1,tempStr.length-1);
                      this.checkedResIds = tempStr.split(";");
                 }
            },
            checkAll:function(event){
                if(event.target.checked){
                   this.checkedResIds = [];
                   for(let obj of this.allCheckboxIds){
                       this.checkedResIds.push(obj.id);
                   }   
                }else{
                    this.cancelChecked(this.allCheckboxIds);
                }
                this.isIndeterminate = false;
            },
            clearMenuSelected:function(){
                this.treeNodeSelectedHighlight = false;
                this.param.menuId = '';
            },
            allotRoleForResource:function(){
                 let _ = this;
                 _.$confirm('确认将当前选中模块的使用权限分配给角色【'+_.param.role.description+'】吗？', '提示', 
                     {
                            confirmButtonText: '确认',
					        cancelButtonText: '取消',
                            type: 'warning'
                          }).then(function(){
                               let strCheckedResIds = '';
                                if(_.checkedResIds && _.checkedResIds.length > 0){
                                    for(let i=0;i<_.checkedResIds.length;i++){
                                        //经协商后台不需要区别类型，所以这里去掉拼接符
                                        let temp = _.checkedResIds[i].replace("MENU-","").replace("ACTION-","");
                                        //菜单id，携带了父菜单的id
                                        let tempArr = temp.split(",");
                                        for(let j=0;j<tempArr.length;j++){
                                            //因为菜单id携带了父菜单id，而每一个模块可能有相同的父
                                            if((","+strCheckedResIds+",").indexOf(","+tempArr[j]+",")==-1){
                                                if(strCheckedResIds.length!=0){
                                                    strCheckedResIds+=",";
                                                }
                                                strCheckedResIds +=tempArr[j];
                                            }
                                        }
                                    }
                                }
                             
                              _.$$Http.post.call(_,'/admin/roleResourceRelation/build',{id:_.param.role.id,resourceIDs:strCheckedResIds})
                                  .then(function(content){
                                      _.$message({showClose: true,message: '操作成功', type: 'info'});
                                  },function(resp){
                                            if(!resp.treated)
                                            _.$alert(resp.message, '提示', {confirmButtonText: '确定'});
                                  });
				          }).catch(function(e){});
            }
        }
    }
</script>