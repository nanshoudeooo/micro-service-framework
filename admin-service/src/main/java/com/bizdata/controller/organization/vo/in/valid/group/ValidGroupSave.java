package com.bizdata.controller.organization.vo.in.valid.group;

import com.bizdata.controller.organization.vo.in.valid.field.ValidFieldName;
import com.bizdata.controller.organization.vo.in.valid.field.ValidFieldParent;

import javax.validation.GroupSequence;

/**
 * 组织机构新增验证组
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
@GroupSequence({ValidFieldName.class, ValidFieldParent.class})
public interface ValidGroupSave {
}
