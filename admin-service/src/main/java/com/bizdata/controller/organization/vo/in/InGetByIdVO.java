package com.bizdata.controller.organization.vo.in;

import com.bizdata.controller.organization.vo.in.valid.field.ValidFieldID;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 根据ID获取组织机构VO
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
public class InGetByIdVO {

    /**
     * 组织机构ID
     */
    @NotBlank(message = "组织机构ID不可为空!", groups = {ValidFieldID.class})
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
