/**
 * 根据菜单权限动态解析路由
 * 上下文在vue或组件的生命周期中
 */
import {MainFrame} from '@/views/frame'


export default function(){
     var menus = this.$store.getters.getMenus;
     if(!menus || menus.length==0){
         return null;
     }
   
     var routes = new Array();
     resolveRoutes(menus,routes);
     //调用addRoutes不会添加到初始化的数组里，
     //需要自己手动修改初始化数组$router.options.routes,
     //但是手动修改routes后刷新页面了数组又会恢复成原来的
     this.$router.addRoutes(routes); 
}

/**
 * 根据菜单解析成路由
 * @param {object[]} menus 
 * @param {object[]} routes 
 */
function resolveRoutes(menus,routes){
    for(let menu of menus){
        //根据菜单构建路由
        var route = {
             path:menu.url.replace(/^\//,'')//将开头的斜杠去掉
        };
       
        if(menu.dir){
            if(menu.root){
               route.component = MainFrame;
               route.path = '/'+route.path;
            }
            if(menu.children && menu.children.length > 0){
                route.children = [{
                 path : "",
                 redirct : menu.children[0].url.replace(/^\//,'')
                }];
                resolveRoutes(menu.children,route.children);
            }
        }else{
           //变更，现在不提供菜单编码，那就将路径去除斜杠当成路由名称
           //route.name = menu.menuCode;
           route.name = menu.url.replace(/\//g,'');//将所有斜杠去掉
           route.meta = {addon:menu.id};//用路由信息对象存储菜单id，用于按钮权限判断
           //变更结束
           route.component = function(resolve){
                 require(['@/views'+menu.url+'/index.js'],resolve);
           }
        }
           //添加路由
        routes.push(route);
    }
}

