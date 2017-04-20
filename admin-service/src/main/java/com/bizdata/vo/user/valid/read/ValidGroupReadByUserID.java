package com.bizdata.vo.user.valid.read;

import com.bizdata.vo.user.valid.ValidFieldID;

import javax.validation.GroupSequence;

/**
 * 用于校验根据ID获取用户信息
 * <p>
 * Created by sdevil507 on 2017/4/20.
 */
@GroupSequence({ValidFieldID.class})
public interface ValidGroupReadByUserID {
}
