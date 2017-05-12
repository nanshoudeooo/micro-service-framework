package com.bizdata.controller.resource.vo.in.valid.group;

import com.bizdata.controller.resource.vo.in.valid.field.*;

import javax.validation.GroupSequence;

/**
 * 用于资源新增校验
 *
 * Created by sdevil507 on 2017/5/5.
 */
@GroupSequence({ValidFieldName.class, ValidFieldUrl.class, ValidFieldPermission.class, ValidFieldSortNum.class, ValidFieldParent.class, ValidFieldMenuType.class, ValidFieldResourceType.class, ValidFieldDir.class})
public interface ValidGroupSave {
}
