package com.bizdata.dao;

import com.bizdata.po.RoleResourceRelation;
import me.sdevil507.base.JpaBaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 根据roleID删除关系
     *
     * @param roleID 角色ID
     */
    @Modifying
    @Transactional
    @Query("delete from RoleResourceRelation r where r.roleID=?1")
    void deleteByRoleID(String roleID);

}
