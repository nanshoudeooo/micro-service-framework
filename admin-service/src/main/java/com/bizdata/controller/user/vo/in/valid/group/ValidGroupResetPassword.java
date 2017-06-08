package com.bizdata.controller.user.vo.in.valid.group;

import com.bizdata.controller.user.vo.in.valid.field.ValidFieldID;
import com.bizdata.controller.user.vo.in.valid.field.ValidFieldPassword;

import javax.validation.GroupSequence;

/**
 * 重置密码校验顺序
 * <p>
 * Created by sdevil507 on 2017/6/8.
 */
@GroupSequence({ValidFieldID.class, ValidFieldPassword.class})
public interface ValidGroupResetPassword {
}
