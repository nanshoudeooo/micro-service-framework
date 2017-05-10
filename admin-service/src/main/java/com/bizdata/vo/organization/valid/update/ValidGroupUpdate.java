package com.bizdata.vo.organization.valid.update;

import com.bizdata.vo.organization.valid.ValidFieldID;
import com.bizdata.vo.organization.valid.ValidFieldName;
import com.bizdata.vo.organization.valid.ValidFieldParent;

import javax.validation.GroupSequence;

/**
 * 组织机构更新验证组
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
@GroupSequence({ValidFieldID.class, ValidFieldName.class, ValidFieldParent.class})
public interface ValidGroupUpdate {
}
