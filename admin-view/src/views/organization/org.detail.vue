<template>
       <panel>
         <el-form ref="myForm"   label-width="100px">
              <el-form-item label="上级机构：">
                <template v-if="org.parent=='0'">无</template>
                <template v-else>{{org.parentName}}</template>
              </el-form-item>
                <el-form-item label="机构名称：">
                {{org.name}}
              </el-form-item>
         </el-form>
       </panel>
</template>
<script>
    export default {
        name:'org-detail',
         data(){
            return {
                 org:{
                     id:'',
                     name:'',
                     parentName:'无',
                     parent:'0'
                 },
                param:{
                    organizationID:''
                }
            };
        },
        watch:{
            param:{
               deep: true,
               handler:function(param){
                    let self = this;
                self.$$Http.post.call(self,'/admin/organization/getByID',{id:param.organizationID})
                .then(function(content){
                    self.$$Func.fillValue(content,self.org);
                },function(resp){
                    self.$$MessageBox.alert(resp.message, '提示', {confirmButtonText: '确定'});	
                });
               }
            }
        },
    }
</script>