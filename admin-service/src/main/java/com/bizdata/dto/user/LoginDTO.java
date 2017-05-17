package com.bizdata.dto.user;

import com.bizdata.controller.user.vo.out.OutLoginVO;

/**
 * Login传输对象
 * <p>
 * Created by sdevil507 on 2017/5/17.
 */
public class LoginDTO {
    /**
     * 执行状态码
     */
    private int code;

    /**
     * 状态对应消息
     */
    private String msg;

    /**
     * 登录成功返回实体对象
     */
    private OutLoginVO outLoginVO;

    public LoginDTO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public LoginDTO(int code, String msg, OutLoginVO outLoginVO) {
        this.code = code;
        this.msg = msg;
        this.outLoginVO = outLoginVO;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OutLoginVO getOutLoginVO() {
        return outLoginVO;
    }

    public void setOutLoginVO(OutLoginVO outLoginVO) {
        this.outLoginVO = outLoginVO;
    }
}
