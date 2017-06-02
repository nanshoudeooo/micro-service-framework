package com.bizdata.dao;

import com.bizdata.po.UserRoleRelation;
import me.sdevil507.base.JpaBaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户角色关系Dao
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
public interface UserRoleRelationDao extends JpaBaseRepository<UserRoleRelation, String> {

    /**
     * 根据用户ID删除用户角色关系
     *
     * @param userID 用户ID
     */
    void deleteByUserID(String userID);

    /**
     * 根据角色ID删除用户角色关系
     *
     * @param roleID 角色ID
     */
    void deleteByRoleID(String roleID);

    /**
     * 根据用户ID获取用户role列表
     *
     * @param userID 用户ID
     * @return List<UserRoleRelation>
     */
    List<UserRoleRelation> findByUserID(String userID);

    /**
     * 根据角色ID获取User列表
     *
     * @param roleID 角色ID
     * @return List<UserRoleRelation>
     */
    List<UserRoleRelation> findByRoleID(String roleID);

    /**
     * 根据用户ID与角色ID删除关系
     *
     * @param userID 用户ID
     * @param roleID 角色ID
     */
    @Modifying
    @Transactional
    @Query("delete from UserRoleRelation u where u.userID=?1 and u.roleID=?2")
    void deleteByUserIDAndRoleID(String userID, String roleID);

}
