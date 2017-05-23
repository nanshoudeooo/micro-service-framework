/**
 * 每个 mutation 都有一个字符串的 事件类型 (type),而这个事件类型可以用常量来代替,为什么用常量？
 * 当在action中触发事件类型时，都是字符传参，如果事件类型名称变了，那么所有调用的方法都要变一下。
 * 另外官网描述可以使 linter 之类的工具发挥作用，同时把这些常量放在单独的文件中可以让你的代码合作者对整个 app 
 * 包含的 mutation 一目了然。
 * 
 */
import * as types from './mutation-types'

export default {
    /**
     * 刷新用户菜单
     * @param {Object} state 
     * @param {Object[]} menus -载荷，这里指菜单信息
     */
    [types.REFRESH_USER_MENUS](state,menus){
        state.menus = menus;
    },
    /**
     * 刷新用户菜单操作权限
     * @param {Object} state 
     * @param {Object} menuOperations -载荷，这里指菜单操作权限
     */
    [types.REFRESH_MENU_OPERATIONS](state,menuOperations){
        state.operations = menuOperations;
    },
    /**
     * 刷新菜单列级
     * @param {Object} state 
     * @param {Object} menuColumns -载荷，这里指主机构
     */
    [types.REFRESH_MENU_COLUMNS](state,menuColumns){
       state.columns = menuColumns;
    }, 
     [types.CLEAR_USER_MENUS](state){
        state.menus = [];
    },
     [types.CLEAR_MENU_OPERATIONS](state){
        state.operations = [];
    },
    [types.CLEAR_MENU_COLUMNS](state){
        state.columns = [];
    }
}