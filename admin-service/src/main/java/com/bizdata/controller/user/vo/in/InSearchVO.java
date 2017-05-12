package com.bizdata.controller.user.vo.in;

/**
 * 用户查询VO
 * <p>
 * Created by sdevil507 on 2017/4/21.
 */
public class InSearchVO {
    /**
     * 用户名
     */
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "InSearchVO{" +
                "username='" + username + '\'' +
                '}';
    }
}
