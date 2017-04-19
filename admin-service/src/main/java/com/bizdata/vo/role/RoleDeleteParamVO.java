package com.bizdata.vo.role;

import com.bizdata.vo.role.valid.ValidFieldID;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 角色删除VO
 * <p>
 * Created by sdevil507 on 2017/4/15.
 */
public class RoleDeleteParamVO {

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
