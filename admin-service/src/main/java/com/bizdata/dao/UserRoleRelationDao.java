package com.bizdata.dao;

import com.bizdata.entity.UserRoleRelation;
import com.bizdata.jpa.base.JpaBaseRepository;

import java.util.List;

/**
 * 用户角色关系Dao
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
public interface UserRoleRelationDao extends JpaBaseRepository<UserRoleRelation, String> {

    void deleteByUserID(String userID);

    /**
     * 根据用户ID获取用户role列表
     *
     * @param userID 用户ID
     * @return List<UserRoleRelation>
     */
    List<UserRoleRelation> findByUserID(String userID);
}
