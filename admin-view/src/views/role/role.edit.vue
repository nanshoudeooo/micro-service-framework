<template>
    <panel style="border:1px solid #ccc;margin-top:1.5px">
       <el-form ref="myForm" :model="role" :rules="rules" label-width="160px" class="bd-form">
          <el-form-item  class="rowline" label="角色名" prop="rolename">
             <el-input v-model="role.rolename" :disabled="true"></el-input>
          </el-form-item>
           <el-form-item  class="rowline" label="描述" prop="description">
             <el-input  v-model="role.description"></el-input>
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
    export default{
        name:'role-edit',
        data:function(){
            let self = this;
            return {
                  role:{
                     id:'',
                     rolename:'',
                     description:''
                 },
                 oldRole:{},
                rules: {
                      rolename:[
                         { required: true, message: '请输入角色名', trigger: 'blur' }
                     ],
                     description:[
                         {required: true, message: '请输入描述', trigger: 'blur'}
                     ]
                 }
            };
        },
        mounted:function(){
            //该值是toolbar组件中获取grid的选中行，然后传过来的,是数组类型。改变数据后需要等渲染响应，使用nextTick
            this.$nextTick(function(){
                 let rowdatas = this.$options["rowdatas"],self = this;
                 if(rowdatas && rowdatas.length > 0){
                    self.$$Http.post.call(self,'/admin/role/getByID',{id:rowdatas[0].id})
                    .then(function(content){
                            self.$$Func.fillValue(content,self.role);
                           self.oldRole = content;
                    },function(resp){
                        if(!resp.treated)
                           _.$alert(resp.message, '提示', {confirmButtonText: '确定'});
                   });
                 }
            });
        },
        methods:{
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
                     self.$$Http.post.call(self,'/admin/role/update',self.role)
                     .then(function(content){//响应成功
                        self.formSubmiting = false;
                        self.$parent.reload();
                     },function(resp){//响应失败
                         self.formSubmiting = false;
                         if(!resp.treated)
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
              this.$$Func.fillValue(this.oldRole,this.role);
            }
        }
    }
</script>