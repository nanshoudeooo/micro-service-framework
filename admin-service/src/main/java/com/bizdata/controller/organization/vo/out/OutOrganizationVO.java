package com.bizdata.controller.organization.vo.out;

import java.util.Date;

/**
 * 根据ID获取组织机构结果VO
 * <p>
 * Created by sdevil507 on 2017/5/10.
 */
public class OutOrganizationVO {

    /**
     * 组织机构ID
     */
    private String id;

    /**
     * 组织机构名称
     */
    private String name;

    /**
     * 组织机构父ID
     */
    private String parent;

    /**
     * 组织机构父节点名称
     */
    private String parentName;

    /**
     * 组织机构创建时间
     */
    private Date createTime;

    /**
     * 是否系统内置
     */
    private boolean builtIn = false;

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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Date getCreateTime() {
        return (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if (null != createTime)
            this.createTime = (Date) createTime.clone();
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(boolean builtIn) {
        this.builtIn = builtIn;
    }
}
