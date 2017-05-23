/**
 * 
 */
import * as types from './mutation-types'
import {$$Http,$$SessionStore} from '@/util';

export default {
  /**
   * 刷新用户信息（角色信息也在此准备）
   * @param {object} context 
   * @returns Promise对象
   */
  refreshUser(context) {
    //提交修改状态
    context.dispatch('assignMessageText','正在初始化用户信息');

    return new Promise(function(resolve){
        //请求用户信息
        $$Http.post('/admin/user/readSelf').then(function(userInfo){
          if(userInfo){
              context.commit(types.REFRESH_USER_INFO,userInfo);//修改用户信息状态
              $$SessionStore.set('user',userInfo);
              context.dispatch('assignMessageText','用户信息初始化结束');
              resolve();
          }
        });
    });
  },
  clearUserInfo(context){
      context.commit(types.CLEAR_USER_INFO);
      $$SessionStore.remove('user');
  },
  refreshRoles(context) {
     //提交修改状态
    context.dispatch('assignMessageText','正在初始化角色信息');

    return new Promise(function(resolve){
        //请求用户信息
        $$Http.post('/admin/role/readSelf').then(function(roles){
          if(roles){
              context.commit(types.REFRESH_ROLES,roles);//修改角色信息状态
              $$SessionStore.set('roles',roles);
              context.dispatch('assignMessageText','角色信息初始化结束');
              resolve();
          }
        });
    });
  },
  clearRoles(context){
     context.commit(types.CLEAR_ROLES);
     $$SessionStore.remove('roles');
  },
  assignToken(context,token) {
    $$SessionStore.set('token',token);
    context.commit(types.ASSIGN_TOKEN,token);
  },
  clearToken(context) {
    context.commit(types.CLEAR_TOKEN);
    $$SessionStore.remove('token');
  }
};