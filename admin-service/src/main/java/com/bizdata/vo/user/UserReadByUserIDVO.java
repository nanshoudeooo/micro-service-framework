package com.bizdata.vo.user;

import com.bizdata.vo.user.valid.ValidFieldID;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 根据用户ID获取用户信息VO
 * <p>
 * Created by sdevil507 on 2017/4/20.
 */
public class UserReadByUserIDVO {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户id必须提供!", groups = ValidFieldID.class)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
