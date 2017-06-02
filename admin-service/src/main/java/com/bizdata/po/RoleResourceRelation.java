package com.bizdata.po;

import me.sdevil507.base.JpaUUIDBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色资源关系实体
 * <p>
 * Created by sdevil507 on 2017/4/16.
 */
@Entity
@Table(name = "admin_role_resource")
public class RoleResourceRelation extends JpaUUIDBaseEntity {

    /**
     * 角色ID
     */
    private String roleID;

    /**
     * 资源ID
     */
    private String resourceID;

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }
}
