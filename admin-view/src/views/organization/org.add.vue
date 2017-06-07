<template>
    <panel style="border:1px solid #ccc;margin-top:1.5px">
         <el-form ref="myForm" :model="org" :rules="rules" label-width="160px" class="bd-form">
          <el-form-item  class="rowline" label="上级机构" prop="parent">
               <el-input @focus="showOrgTree" v-model="org.parentName" readonly></el-input>
               <input type="hidden" v-model="org.parent"/>
          </el-form-item>
           <el-form-item  class="rowline" label="机构名称" prop="name">
             <el-input  v-model="org.name"></el-input>
          </el-form-item>
           <el-form-item>
             <el-button type="primary" @click="submitForm('myForm')" :loading="formSubmiting">保存</el-button>
             <el-button @click="resetForm('myForm')">重置</el-button>
             <el-button @click="backout">返回</el-button>
           </el-form-item>
       </el-form>
    </panel>
</template>
<script>
    import orgTree from './org.tree.vue'
    export default {
        name:'org-add',
        data:function(){
            let self = this;
            return {
                org:{
                     parent:'0',
                     parentName:'无',
                     name:''
                 },
                 rules: {
                     parent:[
                         {required: true, message: '请填写上级机构', trigger: 'change'}
                     ],
                     name:[
                         {required: true, message: '请填写机构名称', trigger: 'blur'}
                     ]
                 }
            };
        },
        methods:{
            showOrgTree:function(event){
                 //让input失去焦点，不然切换视窗的时候，会再次触发弹框
                event.target.blur();
                let self = this;
               this.$$Dialog.open({
                     title:"选择上级机构",
                     content:orgTree,
                     width:"200px",
                     height:"400px",
                     param:{initCheckedId:self.org.parent},
                     okCallback:function(component){
                          if(component.getChecked){
                              let checked = component.getChecked();
                              if(checked.length == 1){
                                 self.org.parent = checked[0].id;
                                 self.org.parentName = checked[0].name;
                              }else{
                                  self.org.parent = "0";
                                  self.org.parentName = "无";
                              }
                          }
                          return true;
                     }
               });
            },
            /**
             * 回退
             */
            backout:function(){
                this.$parent.reload();
            },
            /**
             * 提交表单
             */
            submitForm:function(formName) {
                let self = this; 
              this.$refs[formName].validate(function(valid){
                if (valid) {
                     //禁用按钮防止重复提交
                     self.formSubmiting = true;
                     //发送后台请求
                     self.$$Http.post.call(self,'/admin/organization/save',self.org)
                     .then(function(content){//响应成功
                        self.formSubmiting = false;
                        self.$parent.reload();
                     },function(resp){//响应失败
                         self.formSubmiting = false;
                         self.$alert(resp.message, '提示', {confirmButtonText: '确定'});	
                     }); 
                }
              });
            },
            /**
             * 重置表单
             */
            resetForm:function(formName) {
              this.$refs[formName].resetFields();
              this.org.parentName = "无";
            }
        }
    }
</script>