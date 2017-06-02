package com.bizdata.po;

import me.sdevil507.base.JpaUUIDBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户实体类
 * <p>
 * Created by sdevil507 on 2017/4/7.
 */

@Entity
@Table(name = "admin_user")
public class User extends JpaUUIDBaseEntity {

    /**
     * 用户名
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 密码
     */
    @Column(nullable = false)
    private String password;

    /**
     * 真实姓名
     */
    @Column(nullable = false)
    private String realName;

    /**
     * 邮箱
     */
    @Column
    private String email;

    /**
     * 创建时间
     */
    @Column
    private Date createTime;

    /**
     * 创建者
     */
    @Column
    private String creator;

    /**
     * 最后登录时间
     */
    @Column
    private Date lastLoginTime;

    /**
     * 最后修改时间
     */
    @Column
    private Date lastUpdateTime;

    /**
     * 是否可用
     */
    @Column
    private boolean available=true;

    /**
     * 是否系统内置账户
     */
    @Column
    private boolean builtIn = false;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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
}
