package com.bizdata.controller.organization.vo.in;

import com.bizdata.controller.organization.vo.in.valid.field.ValidFieldID;
import com.bizdata.controller.organization.vo.in.valid.field.ValidFieldName;
import com.bizdata.controller.organization.vo.in.valid.field.ValidFieldParent;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 组织机构更新VO
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
public class InUpdateVO {

    /**
     * 组织机构id
     */
    @NotBlank(message = "组织机构ID不可为空!", groups = {ValidFieldID.class})
    private String id;

    /**
     * 组织机构名称
     */
    @NotBlank(message = "组织机构名称不可为空!", groups = {ValidFieldName.class})
    private String name;

    /**
     * 父ID
     */
    @NotBlank(message = "组织机构父ID不可为空!", groups = {ValidFieldParent.class})
    private String parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
