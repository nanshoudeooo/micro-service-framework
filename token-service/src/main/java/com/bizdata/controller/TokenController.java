package com.bizdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bizdata.service.TokenService;

/**
 * Token管理接口
 *
 * @author sdevil507
 */
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * 传入用户id,创建token
     *
     * @param userID 用户ID
     * @return
     */
    @RequestMapping(value = "/createToken", method = RequestMethod.POST)
    public String createToken(@RequestParam("userID") String userID) {
        return tokenService.createToken(userID);
    }

    /**
     * 移除token
     *
     * @param token token
     * @return
     */
    @RequestMapping(value = "/removeToken", method = RequestMethod.POST)
    public boolean removeToken(@RequestParam("token") String token) {
        return tokenService.removeToken(token);
    }

    /**
     * token自动续租
     *
     * @param token token
     * @return
     */
    @RequestMapping(value = "/autopay", method = RequestMethod.POST)
    public boolean tokenAutoPay(@RequestParam("token") String token) {
        return tokenService.tokenAutoPay(token);
    }

    /**
     * 根据token获取userID
     *
     * @param token token
     * @return
     */
    @RequestMapping(value = "/getUserId", method = RequestMethod.POST)
    public String getUserIdByToken(@RequestParam("token") String token) {
        String userID = tokenService.getUserID(token);
        return userID;
    }

    /**
     * 判断token是否存在
     *
     * @param token token
     * @return
     */
    @RequestMapping(value = "/exist", method = RequestMethod.POST)
    public boolean checkTokenExist(@RequestParam("token") String token) {
        boolean state = tokenService.checkTokenExist(token);
        return state;
    }

}
