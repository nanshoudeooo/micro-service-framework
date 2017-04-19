package com.bizdata.entity;

import com.bizdata.jpa.base.JpaBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色实体
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
@Entity
@Table(name = "admin_role")
public class Role extends JpaBaseEntity {

    /**
     * 角色名称
     */
    @Column(nullable = false)
    private String rolename;

    /**
     * 角色描述
     */
    @Column(nullable = false)
    private String description;

    /**
     * 是否为系统内置角色
     */
    @Column(nullable = false)
    private boolean builtIn = false;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(boolean builtIn) {
        this.builtIn = builtIn;
    }
}
