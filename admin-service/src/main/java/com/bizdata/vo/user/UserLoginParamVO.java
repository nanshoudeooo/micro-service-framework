package com.bizdata.vo.user;

import com.bizdata.vo.user.valid.ValidFieldPassword;
import com.bizdata.vo.user.valid.ValidFieldUsername;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 登录操作提交参数VO
 * <p>
 * Created by sdevil507 on 2017/4/13.
 */
public class UserLoginParamVO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不可以为空", groups = {ValidFieldUsername.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不可以为空", groups = {ValidFieldPassword.class})
    private String password;

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
}
