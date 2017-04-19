package com.bizdata.vo.role;

import com.bizdata.vo.role.valid.ValidFieldDescription;
import com.bizdata.vo.role.valid.ValidFieldID;
import com.bizdata.vo.role.valid.ValidFieldRolename;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 角色更新入参VO
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
public class RoleUpdateParamVO implements Serializable {

    @NotBlank(message = "角色ID不可为空!", groups = {ValidFieldID.class})
    private String id;

    @NotBlank(message = "角色名不可为空!", groups = {ValidFieldRolename.class})
    private String rolename;

    @NotBlank(message = "角色描述不可为空!", groups = {ValidFieldDescription.class})
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
