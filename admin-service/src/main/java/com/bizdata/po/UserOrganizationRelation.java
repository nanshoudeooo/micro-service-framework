package com.bizdata.po;

import com.bizdata.jpa.base.JpaBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户组织机构关系实体
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
@Entity
@Table(name = "admin_user_organization")
public class UserOrganizationRelation extends JpaBaseEntity {
    /**
     * 用户ID
     */
    private String userID;

    /**
     * 组织机构ID
     */
    private String organizationID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }
}
