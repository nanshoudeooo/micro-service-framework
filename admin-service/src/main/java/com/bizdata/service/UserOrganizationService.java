package com.bizdata.service;

import java.util.List;

/**
 * 用户组织机构关系Service
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
public interface UserOrganizationService {

    /**
     * 递归查询出该组织机构下所有用户id
     *
     * @param organizationID 组织机构ID
     * @return List<String>
     */
    List<String> findAllUserIDsRecursion(String organizationID);
}
