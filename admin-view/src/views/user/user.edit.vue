<template>
    <panel style="border:1px solid #ccc;margin-top:1.5px">
       <el-form ref="myForm" :model="user" :rules="rules" label-width="160px" class="bd-form">
           <el-form-item  class="rowline" label="真实姓名" prop="realName">
             <el-input v-model="user.realName"></el-input>
          </el-form-item>
          <el-form-item  class="rowline" label="用户名" prop="username" >
             <el-input v-model="user.username"  :disabled="true"></el-input>
          </el-form-item>
           <el-form-item  class="rowline" label="密码" prop="password">
             <el-input type="password" v-model="user.password" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item class="rowline" label="所属机构" prop="organizationIDs">
              <el-input @focus="showOrgTree" v-model="user.organizationNames" readonly></el-input>
              <input type="hidden" v-model="user.organizationIDs"/>
          </el-form-item>
           <el-form-item class="rowline" label="邮件" prop="email">
             <el-input v-model="user.email"></el-input>
          </el-form-item>
           <el-form-item class="rowline" label="是否可用" prop="available">
             <el-radio-group v-model="user.available">
               <el-radio :label="true">是</el-radio>
               <el-radio :label="false">否</el-radio>
             </el-radio-group>
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
    import orgTree from '@/views/organization/org.tree.vue';
    export default{
        name:'user-edit',
        data:function(){
            let self = this;
            return {
                 user:{
                     id:'',
                     realName:'',
                     username:'',
                     password:'',
                      organizationIDs:'',
                     organizationNames:'',
                     email:'',
                     available:true
                 },
                 oldUser:{},
                 rules: {
                     realName:[
                          { required: true, message: '请输入真实姓名', trigger: 'blur' }
                     ],
                     username:[
                         { required: true, message: '请输入用户名', trigger: 'blur' },
                         { pattern:/[a-zA-Z][a-zA-Z0-9]{3,19}/,message:'请输入首字母为英文的4~20位英文或数字的字符'}
                     ],
                     password:[
                         {required: true, message: '请输入密码', trigger: 'blur'}
                     ],
                     organizationIDs:[
                           {required: true, message: '请选择部门', trigger: 'change'}
                     ],
                     email:[
                          { type:'email',  message: '邮件格式不正确', trigger: 'blur' }
                     ]
                 }
            };
        },
        mounted:function(){
            //该值是toolbar组件中获取grid的选中行，然后传过来的,是数组类型。改变数据后需要等渲染响应，使用nextTick
            this.$nextTick(function(){
                 let rowdatas = this.$options["rowdatas"],self = this;
                 if(rowdatas && rowdatas.length > 0){
                    self.$$Http.post.call(self,'/admin/user/getByID',{id:rowdatas[0].id})
                    .then(function(content){
                           let orgs = content.outIncludedOrganizationVOs;
                           delete content.outIncludedOrganizationVOs;//删除该属性
                           
                           let ids = "",names="";
                           if(orgs && orgs.length > 0){
                               for(let i=0;i<orgs.length;i++){
                                  ids+=orgs[i].id;
                                     names+=orgs[i].name;
                                     if(i < orgs.length - 1){
                                         ids+=",";
                                         names+=",";
                                     }
                               }
                               content.organizationIDs = ids;
                               content.organizationNames = names;
                           }

                           self.$$Func.fillValue(content,self.user);
                           self.oldUser = content;

                            

                    },function(resp){//响应失败
                         if(!resp.treated)
                            self.$$MessageBox.alert(resp.message, '提示', {confirmButtonText: '确定'});	
                     });
                 }
            });
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
                     param:{initCheckedId:self.user.organizationIDs,multi:true},
                     okCallback:function(component){
                          if(component.getChecked){
                              let checked = component.getChecked();
                              if(checked.length > 0){
                                  let ids = "",names="";
                                 for(let i=0;i<checked.length;i++){
                                     ids+=checked[i].id;
                                     names+=checked[i].name;
                                     if(i < checked.length - 1){
                                         ids+=",";
                                         names+=",";
                                     }
                                 }
                                 self.user.organizationIDs = ids;
                                 self.user.organizationNames = names;
                              }else{
                                 self.user.organizationIDs = "";
                                 self.user.organizationNames = "";
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
                     self.$$Http.post.call(self,'/admin/user/update',self.user)
                     .then(function(content){//响应成功
                        self.formSubmiting = false;
                        self.$parent.reload();
                     },function(resp){//响应失败
                         self.formSubmiting = false;
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
              this.$$Func.fillValue(this.oldUser,this.user);
            }
        }
    }
</script>