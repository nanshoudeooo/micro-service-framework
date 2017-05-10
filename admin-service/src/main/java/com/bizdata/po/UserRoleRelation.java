package com.bizdata.po;

import com.bizdata.jpa.base.JpaBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户角色关系实体
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
@Entity
@Table(name = "admin_user_role")
public class UserRoleRelation extends JpaBaseEntity {

    /**
     * 用户id
     */
    private String userID;

    /**
     * 角色id
     */
    private String roleID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
}
