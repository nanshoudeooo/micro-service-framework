<template>
     <a class="tool-btn" :class="{disable:!available}" @click="action" v-if="visible">
            <span class="btn-icon"><icon :name="icon"></icon></span>
            <label class="btn-title">
              <slot></slot>
            </label>
    </a>
</template>
<style scoped>
  .tool-btn{
    display: inline-block;
    margin:2px 2px 0;
    padding-left:10px; 
    padding-right:10px;
    height:36px;
    padding-top:3px; 
    overflow:hidden;
    border:1px solid #F7F7F7;
    cursor: pointer;
    min-width: 30px;
     text-align: center;
}
 .tool-btn:hover{
    -moz-border-radius:5px; 
    -webkit-border-radius:5px; 
    border-radius:5px; 
    background-color:#fff;
    border:1px solid #ccc;
}
.disable.tool-btn{
    -moz-border-radius:0; 
    -webkit-border-radius:0; 
    border-radius:0; 
    background-color:inherit;
    border:none;
    cursor:not-allowed
} 
.btn-icon{
   width:16px;
   height: 16px;
    color: #1481B6;
}
.disable .btn-icon{
      color: #ccc;
}
.btn-title{
   font-size:14px;
   cursor: pointer;
   display: block;
   height:20px;
   width: 100%;
   text-align: center;
   color: #999;
}
.disable .btn-title{
    color:#ccc;
    cursor:not-allowed;
}
</style>
<script>
    export default{
        name:"tool-bar-item",
        componentName:'tool-bar-item',
        props:{
            //按钮图标
            icon:{
                type:String,
                required: true
            },
            //按钮显示需要的权限表达式
            auth:String,
            //关联模块对象
            rel:String,
            //触发需要的datagrid选中的记录数none,single,multi
            trigger:{
                type:String,
                default:'none'
            },
            //显示目标 currentPanel，dialog,confirm，custom
            target:{
                type:String,
                default:'currentPanel'
            },
            //提示内容
            title:String
        },
        data(){
           return {
               available:true,
               panel:{},
               grid:{}
           }
        },
        computed:{
           visible:function(){
              if(this.$route.meta.addon){
                  let currentMenuId = this.$route.meta.addon;
                  //如果是空
                  if(!this.auth || this.auth.length==0){
                         return true;
                  }
                  //如果是判断操作权限
                  else if(this.auth.indexOf('hasPermission') > -1){
                         let operations = this.$store.state.permission.operations;
                          //获取当前对应模块的权限操作列表
                        let currentModuleOpts = operations["menu_"+currentMenuId];
                        if(!currentModuleOpts||currentModuleOpts.length==0){
                            return false;
                        }else{
                            let content = eval(this.auth.match(/.+[(](.+)[)]/)[1]);
                            for(let opt of currentModuleOpts){
                                if(opt.permission == content){
                                    return true;
                                }
                            }
                            return false;
                        }

                  }else if(this.auth.indexOf('hasRole') > -1){
                        let roles = this.$store.getters.getRoles;
                        if(!roles || roles.length == 0){
                            return false;
                        }else{
                            let content = eval(this.auth.match(/.+[(](.+)[)]/)[1]);
                            for(let role of roles){
                                if(content==role.rolename){
                                    return true;
                                }
                            }
                            return false;
                        }
                  }else{
                      return false;
                  }
                
              } 
               return true;
           }
        },
        mounted(){
             var parent = this.$parent;
             while (parent.$options.componentName !== 'panel') {
                  parent = parent.$parent;
             }
             this.panel = parent;
              
             this.grid = parent.$children.find(function(child, index){
                   return child.$options.componentName === 'data-grid';
              });
             if(!this.grid){
                   console.warn("检测不到表格组件对象");
               }
        },
        methods:{
             getGridSelectedRows:function(){
               if( this.grid){
                   return  this.grid.getSelectedRows();
               }else{
                  return new Array();
               }
             },
             action:function(){
                 if(!this.available) return;
                 if(!this.visible) return;

                 if(this.trigger==='custom'){
                      //触发自定义action事件
                    this.$emit('action'); 
                     return;
                 }
                 
                 let flag = true;
                  let selectedRows = this.getGridSelectedRows();
                 if(this.trigger==='single'){
                    if(selectedRows.length==0){
                           //提示选择一条记录
                            this.$message({
                              showClose: true,
                               message: '请选择需要操作的记录',
                               type: 'warning'
                            });
                           flag = false;
                    }
                    if(selectedRows.length > 1){
                        //提示选择不能超过一条记录
                         this.$message({
                              showClose: true,
                               message: '只能选择一条需要操作的记录',
                               type: 'warning'
                            });
                        flag = false;
                    }
                 }else if(this.trigger==='multi'){
                     if(selectedRows.length==0){
                           //提示选择至少一条记录
                           this.$message({
                              showClose: true,
                               message: '请选择需要操作的记录',
                               type: 'warning'
                            });
                           flag = false;
                     }
                 }
               
                 if(flag){
                   //打开目标页
                   this.openTarget();
                    //触发自定义action事件
                    this.$emit('action'); 
                 }
             },
             openTarget:function(){
                   if(this.target==='currentPanel'){
                      //切换panel组件
                      this.panel.key=this.rel;
                      if(this.trigger != 'none'){
                           //将grid选中的值行传给内页
                            let _ = this;
                            this.panel.$nextTick(function(){
                                //获取panel的component组件，然后用options传值
                                this.$refs.currentView.$options["rowdatas"] =  _.getGridSelectedRows();
                            });
                      }
                      
                   }else if(this.target==='dialog'){
                       //弹出对话框
                   }else if(this.target==='confirm'){
                       let _ = this;
                       //提示title内容，然后调用目标组件的submit方法
                       this.$confirm(_.title, '提示', 
					      {
                            confirmButtonText: '确认',
					        cancelButtonText: '取消',
                            type: 'warning'
                          }).then(function(){
                              
                              let rows = _.getGridSelectedRows();
                              // 从panel中找到注入对象，key为rel的，执行submit方法, 将上下文切换为组件对象方便调用插件方法
                              _.panel.inject[_.rel].submit.call(_,_.grid,_.trigger==='single'?rows[0]:rows);
				          }).catch(function(e){
                             // console.dir(e);
                          });
                    }
                   
             }
        }
    }
</script>
