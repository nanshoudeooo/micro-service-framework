<template>
    <el-tree :data="menus" :props="dataMapProp"  show-checkbox  @check-change="oncheckchange"
               :expand-on-click-node="true" check-strictly ref="tree"  node-key="id" default-expand-all>
    </el-tree>
</template>
<script>
     import formateMenu from './menu.formate.js';
    export default {
       name:'menu-tree',
       data(){
           return {
                menus:[],
                dataMapProp:{
                    children: 'children',
                    label: 'name'
                },
                checkedMenu:undefined
           }
       } ,
       props:["param"],
       mounted(){
          let self = this;
          self.$$Http.post.call(self,'/admin/resource/listByResourceTypeAndDir',{resourceType:'MENU',dir:true})
          .then(function(content){
             self.menus = formateMenu(content);
             //默认勾选
             self.$nextTick(function(){
                  if(self.param.initCheckedId){
                       self.$refs.tree.setChecked(self.param.initCheckedId,true);
                  }
             });
          },function(resp){
               self.$$MessageBox.alert(resp.message, '提示', {confirmButtonText: '确定'});	
          });
      },
      methods:{
          oncheckchange:function(data,checked){
              if(checked){
                   let checkedMenus = this.getChecked();
                   if(checkedMenus && checkedMenus.length > 0){
                       for(let item of checkedMenus){
                           if(item.id != data.id){
                                this.$refs.tree.setChecked(item.id,false);
                           }
                       }
                   }
              }
          },
          getChecked:function(){
            return  this.$refs.tree.getCheckedNodes();
          }
      }
    }
</script>