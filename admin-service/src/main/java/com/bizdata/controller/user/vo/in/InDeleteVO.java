package com.bizdata.controller.user.vo.in;

import com.bizdata.controller.user.vo.in.valid.field.ValidFieldID;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 用户删除操作入参VO
 * <p>
 * Created by sdevil507 on 2017/4/13.
 */
public class InDeleteVO {
    /**
     * id
     */
    @NotBlank(message = "{user.id.not_null}", groups = {ValidFieldID.class})
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
