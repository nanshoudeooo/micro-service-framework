import {$$SessionStore} from '@/util';
export default {
   /**
   * 菜单信息
   */
  menus:$$SessionStore.get('menus'),
  /**
   * 操作权限
   */
  operations:$$SessionStore.get('operations'),
  /**
   * 列级权限
   */
  columns:$$SessionStore.get('columns')
};