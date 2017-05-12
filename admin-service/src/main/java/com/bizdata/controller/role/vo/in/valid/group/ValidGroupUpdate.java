package com.bizdata.controller.role.vo.in.valid.group;

import com.bizdata.controller.role.vo.in.valid.field.ValidFieldDescription;
import com.bizdata.controller.role.vo.in.valid.field.ValidFieldID;
import com.bizdata.controller.role.vo.in.valid.field.ValidFieldRolename;

import javax.validation.GroupSequence;

/**
 * 用于顺序校验角色更新操作
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
@GroupSequence({ValidFieldID.class, ValidFieldRolename.class, ValidFieldDescription.class})
public interface ValidGroupUpdate {
}
