package com.bizdata.dao;

import com.bizdata.po.Organization;
import com.bizdata.po.UserOrganizationRelation;
import com.bizdata.jpa.base.JpaBaseRepository;

import java.util.List;

/**
 * 用户组织机构关系Dao
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
public interface UserOrganizationDao extends JpaBaseRepository<UserOrganizationRelation, String> {

    /**
     * 根据组织机构ID条件获取用户组织机构列表
     *
     * @param organizationID 组织机构ID
     * @return List<UserOrganizationRelation>
     */
    List<UserOrganizationRelation> findByOrganizationID(String organizationID);

    /**
     * 根据OrganizationIDs获取用户组织机构关系列表
     *
     * @param OrganizationIDs
     * @return List<Organization>
     */
    List<UserOrganizationRelation> findByOrganizationIDIn(List<String> OrganizationIDs);
}
