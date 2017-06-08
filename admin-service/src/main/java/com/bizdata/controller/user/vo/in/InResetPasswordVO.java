package com.bizdata.controller.user.vo.in;

import com.bizdata.controller.user.vo.in.valid.field.ValidFieldID;
import com.bizdata.controller.user.vo.in.valid.field.ValidFieldPassword;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 重置密码校验
 * <p>
 * Created by sdevil507 on 2017/6/8.
 */
public class InResetPasswordVO {

    /**
     * id
     */
    @NotBlank(message = "{user.id.not_null}", groups = {ValidFieldID.class})
    private String id;

    /**
     * 密码
     */
    @NotBlank(message = "{user.password.not_null}", groups = {ValidFieldPassword.class})
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
