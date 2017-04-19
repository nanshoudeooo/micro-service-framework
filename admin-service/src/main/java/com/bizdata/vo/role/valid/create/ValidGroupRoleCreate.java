package com.bizdata.vo.role.valid.create;

import com.bizdata.vo.role.valid.ValidFieldDescription;
import com.bizdata.vo.role.valid.ValidFieldRolename;

import javax.validation.GroupSequence;

/**
 * 按序校验角色创建
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
@GroupSequence({ValidFieldRolename.class, ValidFieldDescription.class})
public interface ValidGroupRoleCreate {
}
