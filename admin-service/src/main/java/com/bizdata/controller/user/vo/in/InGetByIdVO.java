package com.bizdata.controller.user.vo.in;

import com.bizdata.controller.user.vo.in.valid.field.ValidFieldID;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 根据用户ID获取用户信息VO
 * <p>
 * Created by sdevil507 on 2017/4/20.
 */
public class InGetByIdVO {

    /**
     * 用户ID
     */
    @NotBlank(message = "{user.id.not_null}", groups = ValidFieldID.class)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
