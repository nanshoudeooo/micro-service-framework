package com.bizdata.controller.resource.vo.in.valid.group;

import com.bizdata.controller.resource.vo.in.valid.field.*;

import javax.validation.GroupSequence;

/**
 * Created by sdevil507 on 2017/5/8.
 */
@GroupSequence({ValidFieldID.class, ValidFieldName.class, ValidFieldUrl.class, ValidFieldPermission.class, ValidFieldSortNum.class, ValidFieldParent.class, ValidFieldMenuType.class, ValidFieldResourceType.class, ValidFieldDir.class})
public interface ValidGroupUpdate {
}
