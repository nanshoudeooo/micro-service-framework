package com.bizdata.dao;

import com.bizdata.po.UserOrganizationRelation;
import com.bizdata.jpa.base.JpaBaseRepository;

/**
 * 用户组织机构关系Dao
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
public interface UserOrganizationDao extends JpaBaseRepository<UserOrganizationRelation, String> {
}
