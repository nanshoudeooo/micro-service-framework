<template>
    <panel style="border:1px solid #ccc;margin-top:1.5px">
         <el-form ref="myForm" :model="action" :rules="rules" label-width="100px" class="bd-form">
          <el-form-item  class="rowline" label="所属菜单" prop="parent">
               <el-input  v-model="action.parentName" disabled></el-input>
               <input type="hidden" v-model="action.parent"/>
          </el-form-item>
           <el-form-item  class="rowline" label="操作名称" prop="name">
             <el-input  v-model="action.name"></el-input>
          </el-form-item>
          <el-form-item  class="rowline" label="链接地址" prop="url">
             <el-input  v-model="action.url"></el-input>
          </el-form-item>
          <el-form-item  class="rowline" label="权限标识" prop="permission">
             <el-input  v-model="action.permission"></el-input>
          </el-form-item>
       </el-form>
    </panel>
</template>
<script>
    export default{
        name:'action-form',
        data:function(){
            let self = this;
            return {
                action:{
                     id:'',
                     icon:'',
                     parent:'',
                     parentName:'',
                     menuType:'',
                     name:'',
                     permission:'',
                     resourceType:'ACTION',
                     sortNum:0,
                     url:'',
                     dir:false
                 },
                 oldAction:{},
                 rules: {
                     name:[
                         {required: true, message: '请填写操作名称', trigger: 'blur'}
                     ],
                      url:[
                         {required: true, message: '请填写链接地址', trigger: 'blur'}
                     ],
                     permission:[
                         {required: true, message: '请填写权限标识', trigger: 'blur'}
                     ]
                 }
            };
        },
          props:["param"],
         mounted(){
            if(this.param && this.param.id){//修改
                 let self = this;
                 self.$$Http.post.call(self,'/admin/resource/getByID',{resourceID:self.param.id})
                    .then(function(content){
                           self.$$Func.fillValue(content,self.action);
                           self.oldAction = content;
                    }).catch(function(e){
                            self.$$MessageBox.alert("获取资源信息失败", '提示', {confirmButtonText: '确定'});
                 });
            }else{//新增
               this.action.parent = this.param.parent;
               this.action.parentName = this.param.parentName;
            }
        },
        methods:{
            /**
             * 提交表单
             */
            submitForm:function() {
                let self = this; 
              return new Promise(function(resolve,reject){
                 self.$refs['myForm'].validate(function(valid){
                            if (valid) {
                                let url = ''
                                if(self.action.id){
                                    url = '/admin/resource/update';
                                }else{
                                    url = '/admin/resource/save';
                                }
                            
                                //发送后台请求
                              self.$$Http.post.call(self,url,self.action)
                                .then(function(content){
                                     resolve();
                                },function(resp){
                                     reject(resp);
                                });
                            }
                    });
              });
               
            }
        }
    }
</script>