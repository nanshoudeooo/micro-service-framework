package com.bizdata.controller;

import com.bizdata.token.TokenServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Controller
 * <p>
 * Created by sdevil507 on 2017/5/2.
 */
@RestController
public class AppTestController {
    @Autowired
    private TokenServiceFeign tokenServiceFeign;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void appLogin() {
        System.out.println(111111);
        tokenServiceFeign.createToken("app", "123456");
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void appTest() {
        System.out.println("访问到该接口!");
    }
}
