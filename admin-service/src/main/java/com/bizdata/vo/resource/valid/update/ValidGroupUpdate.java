package com.bizdata.vo.resource.valid.update;

import com.bizdata.vo.resource.valid.*;

import javax.validation.GroupSequence;

/**
 * Created by sdevil507 on 2017/5/8.
 */
@GroupSequence({ValidFieldID.class, ValidFieldName.class, ValidFieldUrl.class, ValidFieldPermission.class, ValidFieldSortNum.class, ValidFieldParent.class, ValidFieldMenuType.class, ValidFieldResourceType.class, ValidFieldDir.class})
public interface ValidGroupUpdate {
}
