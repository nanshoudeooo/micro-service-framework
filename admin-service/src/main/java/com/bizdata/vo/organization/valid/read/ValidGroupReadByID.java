package com.bizdata.vo.organization.valid.read;

import com.bizdata.vo.organization.valid.ValidFieldID;

import javax.validation.GroupSequence;

/**
 * 根据ID获取组织机构验证组
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
@GroupSequence({ValidFieldID.class})
public class ValidGroupReadByID {
}
