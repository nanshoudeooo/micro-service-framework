package com.bizdata.controller.user.vo.in.valid.group;

import com.bizdata.controller.user.vo.in.valid.field.ValidFieldID;

import javax.validation.GroupSequence;

/**
 * 用户删除校验组
 * <p>
 * Created by sdevil507 on 2017/4/10.
 */
@GroupSequence({ValidFieldID.class})
public interface ValidGroupDelete {
}
