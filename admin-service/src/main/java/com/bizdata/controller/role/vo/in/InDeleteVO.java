package com.bizdata.controller.role.vo.in;

import com.bizdata.controller.role.vo.in.valid.field.ValidFieldID;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 角色删除VO
 * <p>
 * Created by sdevil507 on 2017/4/15.
 */
public class InDeleteVO {

    /**
     * 角色id
     */
    @NotBlank(message = "角色id不可为空!", groups = {ValidFieldID.class})
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
