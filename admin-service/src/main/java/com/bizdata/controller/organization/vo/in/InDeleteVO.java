package com.bizdata.controller.organization.vo.in;

import com.bizdata.controller.organization.vo.in.valid.field.ValidFieldID;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 组织机构删除VO
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
public class InDeleteVO {

    /**
     * 组织机构id
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
