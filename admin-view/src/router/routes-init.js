import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login'
import {FrameLoading,MainFrame} from '@/views/frame'
import Dashboard from '@/views/dashboard'
import User from '@/views/user'

Vue.use(Router)

 var router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect:'/mainFrame'
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/logout',
      name: 'logout',
      component: Login
    },
    {
      path:'/frameLoding',
      name:'frameLoading',
      component:FrameLoading
    },
    {
      path:'/mainFrame',
      component:MainFrame,
      children:[
        {
          path:'',
          name:'dashboard',
          component:Dashboard
        }
      ]
    }
  ]
});

export default router;