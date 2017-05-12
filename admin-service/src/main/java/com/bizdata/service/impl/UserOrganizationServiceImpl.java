package com.bizdata.service.impl;

import com.bizdata.dao.OrganizationDao;
import com.bizdata.dao.UserOrganizationDao;
import com.bizdata.po.Organization;
import com.bizdata.po.UserOrganizationRelation;
import com.bizdata.service.UserOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户组织机构关系Service实现
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
@Service
public class UserOrganizationServiceImpl implements UserOrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private UserOrganizationDao userOrganizationDao;

    /**
     * 根据组织机构ID递归查询出所有该节点及子节点下的用户ID列表
     *
     * @param organizationID 组织机构ID
     * @return 用户ID列表
     */
    @Override
    public List<String> findAllUserIDsRecursion(String organizationID) {
        List<String> organizationIDs = new ArrayList<>();
        organizationIDs.add(organizationID);
        organizationIDs.addAll(getNextLevel(organizationDao.findByParent(organizationID)));
        List<String> userIDs = new ArrayList<>();
        List<UserOrganizationRelation> userOrganizationRelations = userOrganizationDao.findByOrganizationIDIn(organizationIDs);
        for (UserOrganizationRelation userOrganizationRelation : userOrganizationRelations) {
            userIDs.add(userOrganizationRelation.getUserID());
        }
        return userIDs;
    }

    /**
     * 递归获取子节点的组织机构ID
     *
     * @param list 组织机构列表
     * @return 组织机构列表ID
     */
    private List<String> getNextLevel(List<Organization> list) {
        List<String> temp_list = new ArrayList<>();
        for (Organization organization : list) {
            temp_list.add(organization.getId());
            temp_list.addAll(getNextLevel(organizationDao.findByParent(organization.getId())));
        }
        return temp_list;
    }
}
