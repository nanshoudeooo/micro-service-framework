package com.bizdata.controller.role.vo.in.valid.group;

import com.bizdata.controller.role.vo.in.valid.field.ValidFieldID;

import javax.validation.GroupSequence;

/**
 * 根据ID获取角色信息验证序列
 * <p>
 * Created by sdevil507 on 2017/6/8.
 */
@GroupSequence({ValidFieldID.class})
public interface ValidGroupGetByID {
}
