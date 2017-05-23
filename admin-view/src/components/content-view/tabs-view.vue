<template>
    <div id="content-wraper">
       <div class="tabs-header">
            <button class="roll-nav roll-left" @click="scrollPrev"><icon name="backward" scale="0.8" style="color:#999999;margin-top:12px;"></icon></button>
            <div class="tabs-nav" ref="navScroll">
               <div ref="nav" :style="navStyle" style="transition: transform .3s;">
                    <template v-for="tab in tabs">
                        <span class="tab-item-nav" :class="{active:tab.id==active}" @click = "activeTab(tab.id)">
                        <icon :name="tab.icon" style="color:#999"></icon>
                        <span class="tab-title">{{tab.title}}</span>
                        <a @click.stop="removeTab(tab.id)" v-if="tab.closable" class="ico-times-box">
                            <icon name="times" scale="0.8" class="ico-times"></icon>
                        </a>
                        </span>  
                    </template>
              </div>
            </div>
             <button class="roll-nav roll-right" @click="scrollNext"><icon name="forward" scale="0.8" style="color:#999999;margin-top:12px;margin-left:5px;"></icon></button>
            <el-dropdown trigger="click" class="tab-nav-btns" @command="handleCommand">
                <span class="el-dropdown-link" style="font-size:12px;color:#999">
                    页签操作<i class="el-icon-caret-bottom el-icon--right" style="font-size:12px;color:#999"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item style="font-size:12px;color:#999" command="refresh">刷新当前页</el-dropdown-item>
                    <el-dropdown-item style="font-size:12px;color:#999" command="closeCurrent">关闭当前页</el-dropdown-item>
                    <el-dropdown-item style="font-size:12px;color:#999" command="closeAll">全部关闭</el-dropdown-item>
                    <el-dropdown-item style="font-size:12px;color:#999" command="closeOther">除此之外全部关闭</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
              <button class="roll-nav fullscreen"><icon name="arrows-alt" scale="0.8" style="color:#999999;margin-top:12px;margin-left:3px;"></icon></button>
       </div>
       <div class="tabs-body">
          <keep-alive :include="acceptComponentNames">
             <router-view></router-view>
           </keep-alive>
       </div>
    </div>
</template>
<style scoped>
    #content-wraper{display:flex;flex-direction:column}
    .tabs-header{
        height: 40px;
        line-height: 40px;
        background: #fafafa;
        border-bottom: solid 2px #438EB9;
        font-size: 12px;
         display: flex;
         overflow:hidden;
         flex:0 0 40px;
    }
    .tabs-header .roll-nav{
        width: 40px;
        height: 40px;
        text-align: center;
        color: #475059;
        background: #fff;
        border: 0;
        line-height: 39px;
        outline: 0;
        cursor:pointer;
    }
    .tabs-header .roll-nav:hover{
        background:#fafafa;
    }
    .tabs-header .tabs-nav{
        flex:1;
        overflow:hidden;
        position:relative;
    }
    .tabs-header .tabs-nav > div{
        white-space: nowrap;
        position:absolute;
    }
    .tabs-header .roll-left{
       border-right: solid 1px #ddd;
        box-shadow:1px 0px 5px #ddd;
    }
    .tabs-header .roll-right{
       border-left: solid 1px #ddd;
       box-shadow:-1px 0px 5px #ddd;
    }
    .tabs-header .fullscreen{
         border-left: solid 1px #ddd;
    }
     .tabs-header .tab-item-nav{
         display:inline-flex;
         padding:0 15px;
         align-items: center;
         cursor:pointer;
     }
     .tabs-header .tab-item-nav .tab-title{
         display:inline-block;
         margin:0 5px;
         line-height:40.5px;
         color:#475059
     }
    .tab-nav-btns{
        padding-right:5px;
        border-left:solid 1px #ddd;
        padding-left:8px;
        cursor:pointer;
    }
     .ico-times-box{
       height:15px;
       line-height:15px;
     }
    .ico-times{
       color:#999;cursor:pointer;
    }
    .ico-times:hover{
        color:red
    }
    .tab-item-nav:hover,.tab-item-nav.active{
        background:#E4E6E9
    }
    .tabs-body{
        flex:1 0 200px;
        background:#ECF0F5;
        overflow:hidden;
    }
</style>
<script>
import {$$SessionStore} from '@/util';
var Home = {
  template: '<p>Welcome home!</p>'
}
export default {
      name:"bd-tabs-view",
      data(){
          return {
             tabs:[],
             active:'',
             currentView:'',
              navStyle: {
                 transform: ''
             }
          }
      },
      computed:{
          acceptComponentNames(){
              var accept = [];
              for(let tab of this.tabs){
                    accept.push(tab.id);
              }
              return accept.join(',');
          }
      },
      methods:{
                /**
                * 添加一个tab对象
                */
                addTab(tab){
                    var tabExisted = this.findTabById(tab.id);
                    if(!tabExisted){
                        this.tabs.push(tab);
                    }
                    this.activeTab(tab.id);
                },
                /**
                *  删除tab对象
                */
                removeTab(id){
                    var idx = this.tabs.findIndex(function(value, index){
                        return value.id == id;
                    });
                    this.tabs.splice(idx,1);//删除数组中元素
                    if(id==this.active){//如果删除的是当前激活的tab，那么需要激活前一个
                        this.activeTab(this.tabs[idx-1].id);//激活前一个tab
                    }
                },
                /**
                * 根据id找到tab
                */
                findTabById:function(id){
                    var tabExisted =  this.tabs.find(function(value, index){
                        return value.id == id;
                    });
                    return tabExisted;
                },
                /**
                * 根据id激活tab
                */
                activeTab(id){
                    var tab =this.findTabById(id);  
                    this.active = id;
                    this.$nextTick(function(){
                         this.scrollToActiveTab()
                    });
                    
                    //通过push将地址栏路径改变
                    this.$router.push(tab.path);
                },
                /**
                * 将路由解析成tab对象
                */
                resolveTab(route){
                    let shortPath = route.path.substring(route.path.lastIndexOf("/"));
                    let menu = this.$store.getters.getPath2Menu[shortPath];
                    
                    let _tabId;
                    for(let obj of route.matched){
                        if(obj.path===route.path){
                            /************************************************************************************
                             *  注意
                             * 1、如果用组件的name作为tabId的话，拷贝文件的时候不注意name就重复了，导致tab打不开很难定位错误
                             * 2、这个变量会关系到组件生命周期缓存问题，不要乱动，注意keep-alive
                             ************************************************************************************/
                           _tabId = obj.components.default.name; //把组件名当做这个tab的唯一标识
                          
                           break;
                        }
                    }

                    if(!menu || menu.dir){
                        return null;
                    }
                    return {
                            id:_tabId,
                            title:menu.name,
                            icon:menu.icon||'lemon-o',
                            closable:true,
                            path:route.path
                        };
                },
                /**
                * 向前滚动
                */
                scrollPrev() {
                    const containerWidth = this.$refs.navScroll.offsetWidth;
                    const currentOffset = this.getCurrentScrollOffset();

                    if (!currentOffset) return;

                    let newOffset = currentOffset > containerWidth
                        ? currentOffset - containerWidth
                        : 0;

                    this.setOffset(newOffset);
                },
                /**
                * 向后滚动
                */
                scrollNext() {
                    const containerWidth = this.$refs.navScroll.offsetWidth;
                    const navWidth = this.$refs.nav.offsetWidth;
                    const currentOffset = this.getCurrentScrollOffset();
                    if (navWidth - currentOffset <= containerWidth) return;

                    let newOffset = navWidth - currentOffset > containerWidth * 2
                    ? currentOffset + containerWidth
                    : (navWidth - containerWidth);

                    this.setOffset(newOffset);
                },
                scrollToActiveTab(){
                    const nav = this.$refs.nav;
                    const activeTab = nav.querySelector('.active');
                    if(!activeTab)return ;
                    
                    const navScroll = this.$refs.navScroll;
                    const activeTabBounding = activeTab.getBoundingClientRect();
                    const navScrollBounding = navScroll.getBoundingClientRect();
                    const navBounding = nav.getBoundingClientRect();
                    const currentOffset = this.getCurrentScrollOffset();
                    let newOffset = currentOffset;

                    if (activeTabBounding.left < navScrollBounding.left) {
                       newOffset = currentOffset - (navScrollBounding.left - activeTabBounding.left);
                    }
                    if (activeTabBounding.right > navScrollBounding.right) {
                       newOffset = currentOffset + activeTabBounding.right - navScrollBounding.right;
                    }
                    if (navBounding.right < navScrollBounding.right) {
                       newOffset = nav.offsetWidth - navScrollBounding.width;
                    }
                    this.setOffset(Math.max(newOffset, 0));
                },
                getCurrentScrollOffset(){
                    const { navStyle } = this;
                    return navStyle.transform
                    ? Number(navStyle.transform.match(/translateX\(-(\d+(\.\d+)*)px\)/)[1])
                    : 0;
                },
                setOffset(value) {
                    this.navStyle.transform = `translateX(-${value}px)`;
                },
                handleCommand(command){
                    if(command==='refresh'){
                         //TODO:
                    }else if(command==='closeCurrent'){
                          this.removeTab(this.active);
                    }else if(command==='closeAll'){
                          this.tabs.splice(1,this.tabs.length-1);
                          this.activeTab("dashboard");
                          this.scrollToActiveTab();
                    }else if(command==='closeOther'){
                          let arr = [];
                          for(let tab of this.tabs){
                              if(tab.id === "dashboard" || tab.id === this.active){
                                  arr.push(tab);
                              }
                          }
                          this.tabs = arr;
                          this.scrollToActiveTab();
                    }
                }
      },
      //钩子函数
      mounted:function(){
           this.addTab({
                id:"dashboard",
                title:"欢迎首页",
                icon:"home",
                closable:false,
                path:'/mainFrame'
            });
            //防止用户刷新浏览器路径，那么我们也打开路径对应的功能
            // let tab = this.resolveTab(this.$route);
            // if(tab){
            //     this.addTab(tab);
            // }
      },
      watch:{
          $route:function(to){
            let tab = this.resolveTab(to);
           if(tab){
                this.addTab(tab);
            }
          }
      }
     }
</script>