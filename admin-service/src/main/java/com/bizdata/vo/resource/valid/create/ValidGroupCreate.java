package com.bizdata.vo.resource.valid.create;

import com.bizdata.vo.resource.valid.*;

import javax.validation.GroupSequence;

/**
 * 用于资源新增校验
 *
 * Created by sdevil507 on 2017/5/5.
 */
@GroupSequence({ValidFieldName.class, ValidFieldUrl.class, ValidFieldPermission.class, ValidFieldSortNum.class, ValidFieldParent.class, ValidFieldMenuType.class, ValidFieldResourceType.class, ValidFieldDir.class})
public interface ValidGroupCreate {
}
