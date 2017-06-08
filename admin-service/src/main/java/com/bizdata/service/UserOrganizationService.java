package com.bizdata.service;

import com.bizdata.controller.user.vo.out.OutIncludedOrganizationVO;

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

    /**
     * 根据获取组织机构列表信息
     *
     * @param userID 用户ID
     * @return 组织机构列表
     * @see OutIncludedOrganizationVO
     */
    List<OutIncludedOrganizationVO> getOutIncludedOrganizationVOs(String userID);
}
