package com.bizdata.controller.user.vo.out;

/**
 * 被包含于用户信息中,用于展示用户所属组织机构
 * <p>
 * Created by sdevil507 on 2017/5/17.
 */
public class OutIncludedOrganizationVO {

    /**
     * 组织机构ID
     */
    private String id;

    /**
     * 组织机构名称
     */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
