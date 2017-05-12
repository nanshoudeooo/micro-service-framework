package com.bizdata.controller.user.vo.in.valid.group;

import com.bizdata.controller.user.vo.in.valid.field.ValidFieldID;

import javax.validation.GroupSequence;

/**
 * 根据ID获取用户信息验证组
 * <p>
 * Created by sdevil507 on 2017/4/20.
 */
@GroupSequence({ValidFieldID.class})
public interface ValidGroupGetByID {
}
