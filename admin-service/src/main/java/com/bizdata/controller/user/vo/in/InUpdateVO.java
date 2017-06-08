package com.bizdata.controller.user.vo.in;

import com.bizdata.controller.user.vo.in.valid.field.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 用户更新操作入参VO
 * <p>
 * Created by sdevil507 on 2017/4/13.
 */
public class InUpdateVO {

    /**
     * id
     */
    @NotBlank(message = "{user.id.not_null}", groups = {ValidFieldID.class})
    private String id;

    /**
     * 用户名
     */
    @NotBlank(message = "{user.username.not_null}", groups = {ValidFieldUsername.class})
    private String username;

    /**
     * 真实姓名
     */
    @NotBlank(message = "{user.realName.not_null}", groups = {ValidFieldRealName.class})
    private String realName;

    /**
     * 密码
     */
    @NotBlank(message = "{user.password.not_null}", groups = {ValidFieldPassword.class})
    private String password;

    /**
     * 邮箱
     */
    @Email(message = "{user.email.not_legal}", groups = {ValidFieldEmail.class})
    private String email;

    /**
     * 是否可用
     */
    private boolean available;

    /**
     * 组织机构ID列表
     */
    @NotNull(message = "{user.organizationIDs.required}", groups = {ValidFieldOrganizationID.class})
    private String[] organizationIDs;

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String[] getOrganizationIDs() {
        return organizationIDs.clone();
    }

    public void setOrganizationIDs(String[] organizationIDs) {
        if (null != organizationIDs)
            this.organizationIDs = organizationIDs.clone();
    }
}
