package com.bizdata.controller.role.vo.in;

import com.bizdata.controller.role.vo.in.valid.field.ValidFieldDescription;
import com.bizdata.controller.role.vo.in.valid.field.ValidFieldRolename;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 角色创建入参VO
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
public class InSaveVO implements Serializable{

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名不可以为空!", groups = {ValidFieldRolename.class})
    private String rolename;

    /**
     * 角色描述
     */
    @NotBlank(message = "角色描述不可以为空!", groups = {ValidFieldDescription.class})
    private String description;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
