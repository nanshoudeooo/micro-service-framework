package com.bizdata.vo.user;

import com.bizdata.vo.user.valid.ValidFieldID;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 用户删除操作入参VO
 * <p>
 * Created by sdevil507 on 2017/4/13.
 */
public class UserDeleteParamVO {
    /**
     * id
     */
    @NotBlank(message = "用户ID不可以为空!", groups = {ValidFieldID.class})
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
