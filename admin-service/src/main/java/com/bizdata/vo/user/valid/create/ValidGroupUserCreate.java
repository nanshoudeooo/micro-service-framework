package com.bizdata.vo.user.valid.create;

import com.bizdata.vo.user.valid.ValidFieldEmail;
import com.bizdata.vo.user.valid.ValidFieldPassword;
import com.bizdata.vo.user.valid.ValidFieldRoleIds;
import com.bizdata.vo.user.valid.ValidFieldUsername;

import javax.validation.GroupSequence;

/**
 * 用于新增用户分组校验
 *
 * Created by sdevil507 on 2017/4/10.
 */
@GroupSequence({ValidFieldUsername.class, ValidFieldPassword.class, ValidFieldEmail.class, ValidFieldRoleIds.class})
public interface ValidGroupUserCreate {
}
