package com.bizdata.vo.role;

/**
 * 角色查询结果VO
 * <p>
 * Created by sdevil507 on 2017/4/15.
 */
public class RoleReadResultVO {
    /**
     * 角色id
     */
    private String id;

    /**
     * 角色名
     */
    private String rolename;

    /**
     * 角色描述
     */
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
