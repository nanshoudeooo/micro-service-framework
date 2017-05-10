package com.bizdata.controller.organization.vo.in;

import com.bizdata.controller.organization.vo.in.valid.field.ValidFieldName;
import com.bizdata.controller.organization.vo.in.valid.field.ValidFieldParent;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 组织机构新增VO
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
public class InSaveVO {

    /**
     * 组织机构名称
     */
    @NotBlank(message = "组织机构名称不可为空!", groups = {ValidFieldName.class})
    private String name;

    /**
     * 父ID
     */
    @NotBlank(message = "父ID不可为空!", groups = {ValidFieldParent.class})
    private String parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
