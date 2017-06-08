package com.bizdata.controller.user.vo.out;

import java.util.Date;
import java.util.List;

/**
 * 用户结果详情VO
 * <p>
 * Created by sdevil507 on 2017/4/13.
 */
public class OutUserVO {

    /**
     * id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * email
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 是否可用
     */
    private boolean available;

    /**
     * 是否系统内置
     */
    private boolean builtIn;

    /**
     * 所属组织机构列表
     */
    private List<OutIncludedOrganizationVO> outIncludedOrganizationVOs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if (null != createTime)
            this.createTime = (Date) createTime.clone();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getLastLoginTime() {
        return (Date) lastLoginTime.clone();
    }

    public void setLastLoginTime(Date lastLoginTime) {
        if (null != lastLoginTime)
            this.lastLoginTime = (Date) lastLoginTime.clone();
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(boolean builtIn) {
        this.builtIn = builtIn;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<OutIncludedOrganizationVO> getOutIncludedOrganizationVOs() {
        return outIncludedOrganizationVOs;
    }

    public void setOutIncludedOrganizationVOs(List<OutIncludedOrganizationVO> outIncludedOrganizationVOs) {
        this.outIncludedOrganizationVOs = outIncludedOrganizationVOs;
    }
}
