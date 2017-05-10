package com.bizdata.vo.organization;

import com.bizdata.vo.organization.valid.ValidFieldName;
import com.bizdata.vo.organization.valid.ValidFieldParent;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 组织机构创建入参
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
public class CreateParamVO {

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
