package com.bizdata.controller.user.vo.in.valid.group;

import com.bizdata.controller.user.vo.in.valid.field.*;

import javax.validation.GroupSequence;

/**
 * 用户更新校验组
 * <p>
 * Created by sdevil507 on 2017/4/10.
 */
@GroupSequence({ValidFieldID.class, ValidFieldUsername.class, ValidFieldRealName.class, ValidFieldPassword.class, ValidFieldEmail.class,ValidFieldOrganizationID.class})
public interface ValidGroupUpdate {
}
