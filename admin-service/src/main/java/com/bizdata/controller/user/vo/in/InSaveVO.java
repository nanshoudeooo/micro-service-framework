package com.bizdata.controller.user.vo.in;

import com.bizdata.controller.user.vo.in.valid.field.ValidFieldEmail;
import com.bizdata.controller.user.vo.in.valid.field.ValidFieldPassword;
import com.bizdata.controller.user.vo.in.valid.field.ValidFieldRealName;
import com.bizdata.controller.user.vo.in.valid.field.ValidFieldUsername;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 用户创建入参VO
 * <p>
 * Created by sdevil507 on 2017/4/13.
 */
public class InSaveVO {

    /**
     * 用户名
     */
    @NotBlank(message = "必须英文字母开头,包含英文或数字,至少4位最多20位!", groups = {ValidFieldUsername.class})
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9]{3,19}", message = "必须英文字母开头,包含英文或数字,至少4位最多20位!", groups = {ValidFieldUsername.class})
    private String username;

    @NotBlank(message = "真实姓名不可为空!", groups = {ValidFieldRealName.class})
    private String realName;

    /**
     * 密码
     */
    @NotBlank(message = "密码包含至少一个大写字母、一个小写字母、一个阿拉伯数字，至少8位、最多16位", groups = {ValidFieldPassword.class})
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{7,15}", message = "密码包含至少一个大写字母、一个小写字母、一个阿拉伯数字，至少8位、最多16位", groups = {ValidFieldPassword.class})
    private String password;

    /**
     * 邮箱
     */
    @Email(message = "email格式不合法", groups = {ValidFieldEmail.class})
    private String email;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
