package com.bizdata.admin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * admin服务接口
 * <p>
 * Created by sdevil507 on 2017/4/20.
 */
@FeignClient(value = "admin-service")
public interface AdminServiceFeign {

    @RequestMapping(value = "/getAuthResourceUrl", method = RequestMethod.POST)
    List<String> getAuthResourceUrl(@RequestParam("userID") String userID);

    @RequestMapping(value = "/getAllResourceUrl", method = RequestMethod.POST)
    List<String> getAllResourceUrl();
}
