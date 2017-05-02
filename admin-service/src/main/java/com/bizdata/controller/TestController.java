package com.bizdata.controller;

import com.bizdata.entity.Test;
import com.bizdata.service.TestService;
import com.bizdata.token.TokenServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sdevil507 on 2017/4/14.
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private TokenServiceFeign tokenServiceFeign;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test() {
        Test test1 = testService.findByName("gjf");
        System.out.println(test1.getName());
        Test test2 = testService.findByName("gjf");
        System.out.println(test1.getName());
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public void test2() {
        Test test1 = testService.findOne("1");
        System.out.println(test1.getName());
        Test test2 = testService.findByName("1");
        System.out.println(test1.getName());
    }

    @RequestMapping(value ="/app/login",method = RequestMethod.POST)
    public void appLogin(){
        tokenServiceFeign.createToken("app","123456");
    }

    @RequestMapping(value = "/app/test",method = RequestMethod.POST)
    public void appTest(){
        System.out.println("访问到该接口!");
    }
}
