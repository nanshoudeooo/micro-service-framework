package com.bizdata.vo.organization.valid.create;

import com.bizdata.vo.organization.valid.ValidFieldName;
import com.bizdata.vo.organization.valid.ValidFieldParent;

import javax.validation.GroupSequence;

/**
 * 组织机构新增验证组
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
@GroupSequence({ValidFieldName.class, ValidFieldParent.class})
public interface ValidGroupCreate {
}
