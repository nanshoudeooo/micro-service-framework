package com.bizdata.dao;

import com.bizdata.po.RoleResourceRelation;
import com.bizdata.jpa.base.JpaBaseRepository;

import java.util.List;

/**
 * 角色资源关系Dao
 * <p>
 * Created by sdevil507 on 2017/4/16.
 */
public interface RoleResourceRelationDao extends JpaBaseRepository<RoleResourceRelation, String> {

    /**
     * 根据角色ID查询出角色资源关系
     *
     * @param roleID 角色ID
     * @return List<RoleResourceRelation>
     */
    List<RoleResourceRelation> findAllByRoleID(String roleID);

    /**
     * 根据资源ID查询出角色资源关系列表
     *
     * @param resourceID 资源ID
     * @return List<RoleResourceRelation>
     */
    List<RoleResourceRelation> findAllByResourceID(String resourceID);

}
