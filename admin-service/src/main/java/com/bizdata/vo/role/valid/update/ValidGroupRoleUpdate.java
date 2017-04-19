package com.bizdata.vo.role.valid.update;

import com.bizdata.vo.role.valid.ValidFieldDescription;
import com.bizdata.vo.role.valid.ValidFieldID;
import com.bizdata.vo.role.valid.ValidFieldRolename;

import javax.validation.GroupSequence;

/**
 * 用于顺序校验角色更新操作
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
@GroupSequence({ValidFieldID.class, ValidFieldRolename.class, ValidFieldDescription.class})
public interface ValidGroupRoleUpdate {
}
