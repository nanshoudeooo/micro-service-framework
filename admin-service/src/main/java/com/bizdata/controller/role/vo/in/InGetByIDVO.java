package com.bizdata.controller.role.vo.in;

import com.bizdata.controller.role.vo.in.valid.field.ValidFieldID;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 根据角色ID获取角色信息校验
 * <p>
 * Created by sdevil507 on 2017/6/8.
 */
public class InGetByIDVO {
    @NotBlank(message = "角色ID不可为空!", groups = {ValidFieldID.class})
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
