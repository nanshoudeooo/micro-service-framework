package com.bizdata.controller.organization.vo.in.valid.group;

import com.bizdata.controller.organization.vo.in.valid.field.ValidFieldID;

import javax.validation.GroupSequence;

/**
 * 根据ID获取组织机构验证组
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
@GroupSequence({ValidFieldID.class})
public interface ValidGroupGetById {
}
