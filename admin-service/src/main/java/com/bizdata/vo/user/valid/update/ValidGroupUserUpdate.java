package com.bizdata.vo.user.valid.update;

import com.bizdata.vo.user.valid.*;

import javax.validation.GroupSequence;

/**
 * 用户更新校验
 *
 * Created by sdevil507 on 2017/4/10.
 */
@GroupSequence({ValidFieldID.class, ValidFieldUsername.class, ValidFieldPassword.class, ValidFieldEmail.class,ValidFieldRoleIds.class})
public interface ValidGroupUserUpdate {
}
