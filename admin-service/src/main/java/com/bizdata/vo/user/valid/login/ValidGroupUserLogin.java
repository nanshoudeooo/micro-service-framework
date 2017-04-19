package com.bizdata.vo.user.valid.login;

import com.bizdata.vo.user.valid.ValidFieldPassword;
import com.bizdata.vo.user.valid.ValidFieldUsername;

import javax.validation.GroupSequence;

/**
 * 登录分组校验
 *
 * Created by sdevil507 on 2017/4/10.
 */
@GroupSequence({ValidFieldUsername.class, ValidFieldPassword.class})
public interface ValidGroupUserLogin {
}
