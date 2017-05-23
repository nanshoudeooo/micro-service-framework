// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import babelpolyfill from 'babel-polyfill'//不加这句ie上跑不起来
import Vue from 'vue'
import store from './store'
import App from './App'
import {router} from './router';
import ElementUI from 'element-ui' // 引入element-ui
import 'element-ui/lib/theme-default/index.css'
import './assets/css/style.css'
import {resolveRoutes} from './router';
import $ from 'jquery';
import moduleUI from './components/module-ui';
import './plugins';
import './mixin';
/*导入所有字体图标 来源GitHub，在ie上有问题,已排查需要在webpack中添加rules：babel-loader*/
import 'vue-awesome/icons'
import Icon from 'vue-awesome/components/Icon.vue'
Vue.component('icon', Icon);


Vue.config.productionTip = false

Vue.use(ElementUI)  //全局使用ElementUI 就可以在其他.vue文件使用element-ui


//路由全局拦截
router.beforeEach(function(to, from, next){
  window.scroll(0, 0);
  
  if(to.path === '/login'){
      if(store.state.principal.token){
           next('/mainFrame');
      }else{
         next();  
      }  
  }else if(to.path === '/logout'){
       store.dispatch('clearToken').then(function(){
          return store.dispatch('clearUserInfo');
        }).then(function(){
           return  store.dispatch('clearRoles');
         }).then(function(){
           return  store.dispatch('clearPermissions');
         }).then(function(){
          // next('/login');  
          //使用go可以达到刷新页面的效果，就如在浏览器中按F5，这样addRoutes或者vuex里的数据就都会清除
           router.go(0);
         });
      
  }else{
    if(store.state.principal.token){
       next();
    }else{
         next('/login');
    }
  }
  
});

/* eslint-disable no-new */
new Vue({
  /**
   * 在页面上被挂载的Dom元素的ID
   */
  el: '#app',
  router,
  /** 
   *把 store 对象提供给 “store” 选项，这可以把 store 的实例注入所有的子组件
   */
  store,
  /**
   * 用该模板替换挂载的Dom元素标签
   */
  template: '<App/>',
  /**
   * 将App组件渲染到目标元素下
   */
  components: { App },
  /**
   * 使用计算属性更改菜单的值
   */
  computed:{
    menus(){
      return store.getters.getMenus;
    }
  },
  /**
   * 监听菜单的变化，主要是监听初次加载菜单是否完成
   * 完成后重新解析路由规则
   */
  watch:{
    menus:{
       deep: true,
       handler:function(){
        resolveRoutes.call(this);
       }
    }
  }, 
  /**
   * 挂载后重新将菜单解析成路由规则，防止用户刷新页面，新增的路由规则
   * 将失效，所以这里重新添加进去
   */
  mounted:function(){
       resolveRoutes.call(this);
  }
})


