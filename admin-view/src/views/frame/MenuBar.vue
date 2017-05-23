<template>
  <div class="menu-bar">
      <el-menu  router unique-opened>
         <template v-for = "menu in menus">
            <el-submenu :index="menu.url" v-if="menu.menuType==='LEFT' && menu.dir">
                  <template slot="title"><i :class="menu.icon"></i>{{menu.name}}</template>
                  <el-menu-item v-for="(submenu,submenuIndex) in menu.children" 
                                :key="submenuIndex"
                                :index="menu.url + submenu.url">
                     {{submenu.name}}
                  </el-menu-item>
            </el-submenu>
            <el-menu-item :index="menu.url" v-else-if="menu.menuType==='LEFT' && menu.dir">
                <i :class="menu.icon"></i>{{menu.name}}
            </el-menu-item>
         </template>
    </el-menu>
  </div>
</template>

<style scoped>
 .menu-bar {
      flex: 0 0 190px;
      background: #F2F2F2;
      border-right: 1px solid #CCCCCC;
  }
</style>

<script>

 export default {
      name:"bd-menubar",
      data(){
        return {
            menus:{},
            watchFlag:false,
        };
      },
      mounted(){
          let allMenus = this.$store.getters.getMenus;
          let topMenus = this.$store.getters.getTopMenus;
          //如果不需要监听url变化
          if(topMenus.length==0){
            this.watchFlag = false;
            this.menus = this.$store.getters.getMenus;
          }else{
            this.watchFlag = true;
          } 
      },
      watch:{
          $route:function(to){
            //如果不需要监听
               if(!this.watchFlag) return;
              
              let allMenus = this.$store.getters.getMenus;
              for(let topMenu of allMenus){
                     if(topMenu.url == to.path){
                        this.menus = topMenu.children;
                     }
               }  
          }
      }
  }
</script>