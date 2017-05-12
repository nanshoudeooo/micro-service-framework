package com.bizdata.controller.user.vo.in.valid.group;

import com.bizdata.controller.user.vo.in.valid.field.ValidFieldPassword;
import com.bizdata.controller.user.vo.in.valid.field.ValidFieldUsername;

import javax.validation.GroupSequence;

/**
 * 用户登录校验组
 * <p>
 * Created by sdevil507 on 2017/4/10.
 */
@GroupSequence({ValidFieldUsername.class, ValidFieldPassword.class})
public interface ValidGroupLogin {
}
