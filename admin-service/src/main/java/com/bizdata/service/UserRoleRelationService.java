package com.bizdata.service;

/**
 * 用户角色关系Service
 * <p>
 * Created by sdevil507 on 2017/4/16.
 */
public interface UserRoleRelationService {

    /**
     * 建立角色用户关系
     *
     * @param roleID  角色ID
     * @param userIDs 用户ID列表
     * @return boolean
     */
    boolean build(String roleID, String[] userIDs);

    /**
     * 根据用户ID删除用户角色关系
     *
     * @param userID 角色ID
     * @return boolean
     */
    boolean deleteByUserID(String userID);

}
