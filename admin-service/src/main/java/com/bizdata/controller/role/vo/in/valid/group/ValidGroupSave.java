package com.bizdata.controller.role.vo.in.valid.group;

import com.bizdata.controller.role.vo.in.valid.field.ValidFieldDescription;
import com.bizdata.controller.role.vo.in.valid.field.ValidFieldRolename;

import javax.validation.GroupSequence;

/**
 * 按序校验角色创建
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
@GroupSequence({ValidFieldRolename.class, ValidFieldDescription.class})
public interface ValidGroupSave {
}
