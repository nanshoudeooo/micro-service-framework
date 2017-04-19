package com.bizdata.vo.user.valid.delete;

import com.bizdata.vo.user.valid.ValidFieldID;

import javax.validation.GroupSequence;

/**
 * 用于删除用户校验
 *
 * Created by sdevil507 on 2017/4/10.
 */
@GroupSequence({ValidFieldID.class})
public interface ValidGroupUserDelete {
}
