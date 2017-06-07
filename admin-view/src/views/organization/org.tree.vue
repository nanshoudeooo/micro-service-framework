<template>
    <el-tree :data="orgs" :props="dataMapProp"  show-checkbox  @check-change="oncheckchange"
               :expand-on-click-node="true" check-strictly ref="tree"  node-key="id" default-expand-all>
    </el-tree>
</template>
<script>
     import formateOrg from './organization.formate.js';
    export default {
       name:'org-tree',
       data(){
           return {
                orgs:[],
                dataMapProp:{
                    children: 'children',
                    label: 'name'
                },
                checkedOrg:undefined
           }
       } ,
       props:["param"],
       mounted(){
          let self = this;
          self.$$Http.post.call(self,'/admin/organization/list')
          .then(function(content){
             self.orgs = formateOrg(content);
             //默认勾选
             self.$nextTick(function(){
                  if(self.param.initCheckedId){
                      if(this.param.multi){
                          for(let id of self.param.initCheckedId){
                               self.$refs.tree.setChecked(id,true);
                          }
                      }else{
                         self.$refs.tree.setChecked(self.param.initCheckedId,true);
                      }
                  }
             });
          },function(resp){
               self.$alert(resp.message, '提示', {confirmButtonText: '确定'});	
          });
      },
      methods:{
          oncheckchange:function(data,checked){
              if(checked && !this.param.multi){
                   let checkedOrg = this.getChecked();
                   if(checkedOrg && checkedOrg.length > 0){
                       for(let item of checkedOrg){
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