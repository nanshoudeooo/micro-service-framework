package com.bizdata.vo.user;

import com.bizdata.vo.user.valid.ValidFieldID;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 根据用户id获取用户信息入参VO
 * <p>
 * Created by sdevil507 on 2017/4/15.
 */
public class UserReadOneParamVO {
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

    @Override
    public String toString() {
        return "UserReadOneParamVO{" +
                "id='" + id + '\'' +
                '}';
    }
}
