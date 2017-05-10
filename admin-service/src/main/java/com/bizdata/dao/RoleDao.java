package com.bizdata.dao;

import com.bizdata.po.Role;
import com.bizdata.jpa.base.JpaBaseRepository;

/**
 * 角色Dao
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
public interface RoleDao extends JpaBaseRepository<Role, String> {

    /**
     * 根据角色名查找Role
     *
     * @param rolename 角色名
     * @return Role
     */
    Role findByRolename(String rolename);
}
