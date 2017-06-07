/**
 * 
 */
import * as types from './mutation-types'
import {$$Http,$$SessionStore} from '@/util';

export default {
   /**
    * 刷新用户所有权限
    * @param {object} context 
    */
   refreshPermissions(context) {
     context.dispatch('assignMessageText','正在加载用户权限数据');

     return new Promise(function(resolve){
        $$Http.post('/admin/resource/listResourceType').then(function(content){
            if(content && content.length > 0){
                var promisArray = new Array();
               
                for(let permType of content){
                    if(permType === 'MENU'){
                       promisArray.push('refreshUserMenus');
                    }else if(permType === 'ACTION'){
                       promisArray.push('refreshOprations');
                    }
                    //可以添加其他权限信息 TODO：
                }
              
              
                Promise.all(promisArray.map(function(promisMethodName){
                    return context.dispatch(promisMethodName);
                })).then(function(){
                     context.dispatch('assignMessageText','权限数据初始化结束');
                     resolve();
                });
            }
      });
    });
      
  },
  clearPermissions(context) {
      context.dispatch('clearMenus');
      context.dispatch('clearOptions');
      context.dispatch('clearColumns');
  },
  /**
   * 刷新用户菜单数据
   * @param {object} context 
   */
  refreshUserMenus(context) {
     //提交修改状态
    context.dispatch('assignMessageText','正在加载用户权限数据,菜单数据准备中');
   
    return new Promise(function(resolve){
        $$Http.post('/admin/resource/listResourceByUserIDAndResourceType',{resourceType:'MENU'}).then(function(content){
              context.commit(types.REFRESH_USER_MENUS,content);
              $$SessionStore.set('menus',content);
              context.dispatch('assignMessageText','正在加载用户权限数据,菜单数据加载结束');

              resolve(); 
        });
    });
  },
 
  /**
   * 刷新操作权限,操作权限存储数据结构为{菜单编码：[操作编码]}
   * @example {'menu_1':['system:user:view','system:user:create','system:user:view']}
   * @param {object} context 
   * @returns Promise对象
   */
  refreshOprations(context) {
    context.dispatch('assignMessageText','正在加载用户权限数据,操作权限数据准备中');

    return new Promise(function(resolve){
        $$Http.post('/admin/resource/listResourceByUserIDAndResourceType',{resourceType:'ACTION'}).then(function(content){
           var operations = {}
           if(content && content.length > 0){
               for(let item of content){
                   let key = 'menu_'+item.parent;
                 if(!operations[key]){
                    operations[key] = new Array();
                 }
                 operations[key].push(item);
               }
           }

          context.commit(types.REFRESH_MENU_OPERATIONS,operations);
          $$SessionStore.set('operations',operations);
          context.dispatch('assignMessageText','正在加载用户权限数据,操作权限数据加载结束');

          resolve(); 
        });
    });
   
  },
  /**
   * 刷新列级权限
   * @param {object} context 
   */
  refreshColumns(context){
     //TODO:
  },
  clearMenus(context){
      context.commit(types.CLEAR_USER_MENUS);
      $$SessionStore.remove('menus');
  },
  clearOptions(context){
     context.commit(types.CLEAR_MENU_OPERATIONS);
      $$SessionStore.remove('options');
  },
  clearColumns(context){
    //    context.commit(types.CLEAR_MENU_COLUMNS);
    //   $$SessionStore.remove('columns');
  }
};