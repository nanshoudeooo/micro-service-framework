<template>
    <panel style="flex-direction:row ">
       <div class="left">
          <div class="block-bar-title" style="border:none">角色</div>
          <ul>
             <template v-for="role in roles">
                   <li @click="selectRole(role)" :class="{selected:role.id==roleSelected.id}">
                     <icon name="user-o" style="height:14px; vertical-align:middle;cursor:pointer"></icon>
                      <label style="vertical-align:middle;margin-left:5px;cursor:pointer">{{role.description}}({{role.rolename}})</label>
                   </li>
             </template>
          </ul>
       </div>
       <div class="right">
          <div class="block-bar-title" style="border:none">角色授权-<span>{{title}}</span></div>
          <div>
                <div class="tabHeader">
                   <div style="position: absolute">
                       <div  class="tabItem" :class="{actived:currentView=='roleMember'}" @click="tabchange('roleMember')">角色成员</div>
                       <div  class="tabItem" :class="{actived:currentView=='module'}" @click="tabchange('module');">模块权限</div>
                   </div>
                </div>
                <div  style="margin-left: 1px; margin-top: 1px; overflow: hidden;height:450px">
                    <div class="tabPanel">
                       <component :is="currentView" ref="componentInst"></component>
                    </div>
               </div>
           </div>
      </div>
    </panel>
</template>
<style scoped>
    .left,.right{ 
        border:1px solid #d1dbe5;
        box-shadow: 0 2px 4px 0 rgba(0,0,0,.12), 0 0 6px 0 rgba(0,0,0,.04);
    }
    .left{
        margin-right:5px;
        width:200px;
       
    }
    .right{
        flex:1;
    }
    .left ul li{
        font-size:14px;
        height:30px;
        padding:3px;
        line-height:30px;
    }
    .left ul li:hover,.left ul li.selected{
        color:#20a0ff;
        background:#edf7ff
    }
 
.tabHeader {
	height: 32px;
	padding-top: 0px;
	padding-right: 0px;
	padding-bottom: 0px;
	padding-left: 4px;
    border: 1px solid #ccc;
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 1px;
	border-left-width: 0px;
    background-color: #F7F7F7;
}

.tabHeader .tabItem {
	cursor:pointer;
	line-height: 15px;
	padding-top: 6px;
	padding-right: 10px;
	padding-bottom: 6px;
	padding-left: 10px;
	font-size: 12px;
	margin-top: 4px;
	margin-right: 5px;
	margin-bottom: 0px;
	margin-left: 0px;
	border-top-width: 1px;
	border-right-width: 1px;
	border-left-width: 1px;
	float: left;
	-moz-border-radius-topright: 7px;
	-moz-border-radius-topleft: 7px;
	-webkit-border-top-right-radius: 7px;
	-webkit-border-top-left-radius: 7px;
	border-top-right-radius: 7px;
	border-top-left-radius: 7px;
    border: 1px solid #ccc;
}

.tabHeader  .tabItem.actived {
	background-color: rgb(255, 255, 255);
}
.tabPanel{height:100%}

</style>
<script>
    import roleMember from './role.member.vue'
    import module from './role.module.vue'

    export default {
        name:'permission',
        data(){
            return {
                currentView:'roleMember',
                roles:[],
                roleSelected:'',
                title:''
            };
        },
        mounted(){
           this.getRoleList();
        },
        methods:{
            getRoleList:function(){
                let self = this;
                self.$$Http.post.call(self,'/admin/role/list')
                .then(function(content){
                    self.roles = content.rows;
                    if(self.roles && self.roles.length > 0){
                        self.selectRole(self.roles[0]);
                    }
                },function(resp){//响应失败
                         if(!resp.treated)
                           self.$$MessageBox.alert(resp.message, '提示', {confirmButtonText: '确定'});	
                });
            },
            selectRole:function(role){
                this.roleSelected = role;
                this.title = role.description;
                let inst = this.$refs.componentInst;
                inst.$set(inst.param,"role",role);
            },
            tabchange:function(cv){
                this.currentView = cv;
                this.$nextTick(function(){
                      let inst = this.$refs.componentInst;
                      inst.$set(inst.param,"role",this.roleSelected);
                });
            }
        },
        components:{
            'roleMember':roleMember,
            'module':module
        }
    }
</script>