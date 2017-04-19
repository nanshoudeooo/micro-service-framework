package com.bizdata.service;

import com.bizdata.entity.UserRoleRelation;

import java.util.List;

/**
 * 用户角色关系Service
 * <p>
 * Created by sdevil507 on 2017/4/16.
 */
public interface UserRoleRelationService {

    boolean buildUserAndRoleRelation(String userID, List<String> roleIds);

    boolean deleteByUserID(String userID);

    List<UserRoleRelation> findByUserID(String userID);
}
