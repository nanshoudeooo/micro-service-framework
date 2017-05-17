package com.bizdata.service;

/**
 * 角色资源关系Service
 * <p>
 * Created by sdevil507 on 2017/4/16.
 */
public interface RoleResourceRelationService {

    /**
     * 角色资源关系绑定
     *
     * @param roleID      角色ID
     * @param resourceIDs 资源ID列表
     * @return boolean执行反馈
     */
    boolean build(String roleID, String[] resourceIDs);
}
