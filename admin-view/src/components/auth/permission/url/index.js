/**
 * 本方法应用场景是：实现定好路由规则，然后根据
 * 用户自己所能访问的菜单来进行路由拦截，防止用户通过
 * 浏览器url访问不属于自己的功能。
 *  
 * 需要配置路由的meta元素（如： meta:{authed:true}）用来排除不需要拦截的初始路由。
 * 
 * 注意：当用addRoutes API时本方法可以不要使用了。
 * 
 * @param {object} route -当前路由对象 
 * @param {object} store - 当前存储对象 
 */
export default function(route,store){
    var canGo = false;
    if(route.meta.authed){
        canGo = true;
    }else{
        if(store.getters.getPath2Menu[route.path]){
             canGo = true;
        }
    }
    
   return canGo;
}