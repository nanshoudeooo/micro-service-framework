<template>
    <panel style="border:1px solid #ccc;margin-top:1.5px">
         <el-form ref="myForm" :model="menu" :rules="rules" label-width="160px" class="bd-form">
          <el-form-item  class="rowline" label="上级菜单" prop="parent">
               <el-input @focus="showMenuTree" v-model="menu.parentName" readonly></el-input>
               <input type="hidden" v-model="menu.parent"/>
          </el-form-item>
           <el-form-item  class="rowline" label="菜单名称" prop="name">
             <el-input  v-model="menu.name"></el-input>
          </el-form-item>
          <el-form-item  class="rowline" label="是否是目录" prop="dir">
             <el-radio-group v-model="menu.dir">
               <el-radio :label="true">是</el-radio>
               <el-radio :label="false">否</el-radio>
             </el-radio-group>
          </el-form-item>
           <el-form-item  class="rowline" label="菜单位置" prop="menuType">
              <el-radio-group v-model="menu.menuType">
               <el-radio label="TOP">顶部</el-radio>
               <el-radio label="LEFT">左侧</el-radio>
             </el-radio-group>
          </el-form-item>
          <el-form-item  class="rowline" label="链接地址" prop="url">
             <el-input  v-model="menu.url"></el-input>
          </el-form-item>
           <el-form-item  class="rowline" label="菜单图标" prop="icon">
             <el-input  v-model="menu.icon"></el-input>
             <span style="font-size:12px;color:#ccc">
               图标采用font awesome，详见<a href="http://fontawesome.io/icons/" target="_blank">http://fontawesome.io/icons/</a>
            </span>
          </el-form-item>
          <el-form-item  class="rowline" label="权限标识" prop="permission">
             <el-input  v-model="menu.permission"></el-input>
          </el-form-item>
          <el-form-item  class="rowline" label="排序序号" prop="sortNum">
             <el-input  v-model.number="menu.sortNum"></el-input>
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
    import menuTree from './menu.tree.vue'
    export default{
        name:'menu-edit',
        data:function(){
            let self = this;
            return {
                menu:{
                     id:'',
                     icon:'',
                     parent:'0',
                     parentName:'无',
                     menuType:'LEFT',
                     name:'',
                     permission:'',
                     resourceType:'MENU',
                     sortNum:0,
                     url:'',
                     dir:false
                 },
                 oldMenu:{},
                 rules: {
                     name:[
                         {required: true, message: '请填写菜单名称', trigger: 'blur'}
                     ],
                      url:[
                         {required: true, message: '请填写链接地址', trigger: 'blur'}
                     ],
                     permission:[
                         {required: true, message: '请填写权限标识', trigger: 'blur'}
                     ],
                     sortNum:[
                         {required: true, message: '请填写排序序号'},
                         { type: 'number', message: '排序序号必须为数字值'}
                     ]
                 }
            };
        },
        mounted(){
            this.$nextTick(function(){
                 let rowdatas = this.$options["rowdatas"],self = this;
                if(rowdatas && rowdatas.length > 0){
                        self.$$Http.post.call(self,'/admin/resource/getByID',{resourceID:rowdatas[0].id})
                        .then(function(content){
                             if(content.parent=='0'){
                                content.parentName="无";
                            }
                            self.$$Func.fillValue(content,self.menu);
                            self.oldMenu = content;
                        },function(resp){
                            if(!resp.treated)
                                _.$$MessageBox.alert(resp.message, '提示', {confirmButtonText: '确定'});
                        });
                }
            });
        },
        methods:{
            showMenuTree:function(event){
                   //让input失去焦点，不然切换视窗的时候，会再次触发弹框
                event.target.blur();
                let self = this;
               this.$$Dialog.open({
                     title:"选择菜单",
                     content:menuTree,
                     width:"200px",
                     height:"400px",
                     param:{initCheckedId:self.menu.parent},
                     okCallback:function(component){
                          if(component.getChecked){
                              let checked = component.getChecked();
                              if(checked.length == 1){
                                 self.menu.parent = checked[0].id;
                                 self.menu.parentName = checked[0].name;
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
                     self.$$Http.post.call(self,'/admin/resource/update',self.menu)
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
                this.$$Func.fillValue(this.oldMenu,this.menu);
            }
        }
    }
</script>