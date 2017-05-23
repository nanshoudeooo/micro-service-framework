<template>
<div id="header">
    <div class="navbar-header">南京商数</div>
     <div>
       <template v-for="menu in menus">
          <router-link  :to="menu.url">
             {{menu.name}}
          </router-link>
       </template>
    </div>
    <div class="navbar-buttons">
      工具
      <a @click="showPersonInfo">个人资料</a> 
      <router-link to="/logout">退出</router-link>
    </div>
</div>
</template>

<style scoped>
    #header {
        flex: 0 0 45px;
        background: #438EB9;
        display: flex;
        justify-content: space-between;
        line-height: 45px;
    }

    #header .navbar-header {
        color: #fff;
        font-weight: bold;
        margin-left: 20px;
        text-shadow: 0px -2px 5px #ccc;
    }

   #header .navbar-buttons {
      margin-right: 20px;
   }
</style>

<script>
  // 在单独构建的版本中辅助函数为 Vuex.mapState
  import { mapGetters } from 'vuex';
  export default {
    name: 'bd-header',
    computed:mapGetters({
        menus:'getTopMenus'
    }),
    methods:{
        showPersonInfo:function(){
            this.$$Dialog.open({
               title:"个人信息",
               contentType:"html",
               content:"hello world"
            });
        }
    }
  }
</script>