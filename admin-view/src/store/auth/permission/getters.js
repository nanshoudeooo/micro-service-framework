import {
  $$SessionStore,
  $$Func
} from '@/util';

import formateMenu from '@/views/resource/menu.formate.js';

export default {
  getMenus(state) {
    if (state.menus && state.menus.length > 0) {

      var menuClone = [];
      for (let item of state.menus) {
        menuClone.push($$Func.deepCopy(item));
      }
      menuClone = formateMenu(menuClone);
      return menuClone;
    } else {
      return Array.of();
    }
  },
  /**
   * 路径的最后一个斜杠与模块的映射
   * @param {*} state 
   */
  getPath2Menu(state) {
    let paths = {};
    let menus = state.menus;
    if (menus && menus.length > 0) {
      for (let menu of menus) {
        if (!menu.dir) {
          paths[menu.url] = menu;
        }
      }
    }
    return paths;
  },
  /**
   * 获取顶级菜单
   * @param {*} state 
   */
  getTopMenus(state){
    let menus = state.menus;
    let result = new Array();
    if (menus && menus.length > 0) {
       for (let menu of menus) {
         if(menu.menuType=="TOP"){
           result.push(menu);
         }
       }
    }
    return result;
  }
}
