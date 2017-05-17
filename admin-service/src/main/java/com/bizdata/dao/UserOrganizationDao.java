package com.bizdata.dao;

import com.bizdata.po.Organization;
import com.bizdata.po.UserOrganizationRelation;
import com.bizdata.jpa.base.JpaBaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 根据UserID获取用户组织机构关系列表
     *
     * @param userID 用户ID
     * @return List<UserOrganizationRelation>
     */
    List<UserOrganizationRelation> findByUserID(String userID);

    /**
     * 根据用户ID删除关系
     *
     * @param userID 角色ID
     */
    @Modifying
    @Transactional
    @Query("delete from UserOrganizationRelation u where u.userID=?1")
    void deleteByUserID(String userID);
}
