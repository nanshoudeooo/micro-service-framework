<template>
     <panel style="border:1px solid #ccc;margin-top:1.5px">
         <el-form ref="myForm" :model="user" :rules="rules" label-width="160px" class="bd-form">
             <el-form-item  class="rowline" label="用户名" prop="username" >
             <el-input v-model="user.username"  :disabled="true"></el-input>
          </el-form-item>
          <el-form-item  class="rowline" label="新密码" prop="password">
             <el-input type="password" v-model="user.password"></el-input>
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
        name:'user-resetPwd',
        data:function(){
            let self = this;
            return {
                 user:{
                     userID:'',
                     username:'',
                     password:''
                 },
                 oldUser:{},
                 rules: {
                     password:[
                         {required: true, message: '请输入密码', trigger: 'blur'},
                         {trigger: 'blur',pattern:/(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{7,15}/,message:'请输入包含大小写字母和数字的8~16位字符'}
                     ]
                 }
            };
        },
        mounted:function(){
            //该值是toolbar组件中获取grid的选中行，然后传过来的,是数组类型。改变数据后需要等渲染响应，使用nextTick
            this.$nextTick(function(){
                 let rowdatas = this.$options["rowdatas"];
                 if(rowdatas && rowdatas.length > 0){
                     this.user.userID = rowdatas[0].id;
                     this.user.username = rowdatas[0].username;
                     
                     this.oldUser = rowdatas[0];
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
                     self.$$Http.post.call(self,'/admin/user/resetPassword',self.user)
                     .then(function(content){//响应成功
                        self.formSubmiting = false;
                        self.$parent.reload();
                     },function(resp){//响应失败
                         self.formSubmiting = false;
                          if(!resp.treated)
                            self.$$MessageBox.alert(resp.message, '提示', {confirmButtonText: '确定'});	
                     }); 
                }
              });
            },
            /**
             * 重置表单
             */
            resetForm:function(formName) {
              this.$refs[formName].resetFields();
              
              this.user.userID = this.oldUser.id;
              this.user.username = this.oldUser.username;
              this.user.password = "";
            }
        }
    }
</script>