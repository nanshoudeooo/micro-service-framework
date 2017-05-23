import {$$SessionStore} from '@/util';

export default {
   /**
   * 用户信息
   */
  user:$$SessionStore.get("user"),
  /**
   * 角色信息
   */
  roles:$$SessionStore.get("roles"),
  /**
   * 令牌
   */
  token:$$SessionStore.get("token")
};