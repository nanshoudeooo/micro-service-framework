package com.bizdata.vo.user;

import java.util.Date;
import java.util.List;

/**
 * 用户列表操作结果VO
 * <p>
 * Created by sdevil507 on 2017/4/13.
 */
public class UserReadResultVO {

    /**
     * id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

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
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
