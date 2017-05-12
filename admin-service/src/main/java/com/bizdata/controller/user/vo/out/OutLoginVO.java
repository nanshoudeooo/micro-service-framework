package com.bizdata.controller.user.vo.out;

/**
 * 登录操作执行结果VO
 * <p>
 * Created by sdevil507 on 2017/4/13.
 */
public class OutLoginVO {

    /**
     * 登录成功返回token
     */
    private String token;

    public OutLoginVO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
