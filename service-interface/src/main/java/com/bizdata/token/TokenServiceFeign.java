package com.bizdata.token;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * token服务接口
 *
 * @author sdevil507
 */
@FeignClient(value = "token-service")
public interface TokenServiceFeign {

    /**
     * 校验token是否存在
     *
     * @param token token
     * @return boolean类型判断值
     */
    @RequestMapping(value = "/exist", method = RequestMethod.POST)
    public boolean checkTokenExist(@RequestParam("token") String token);

    /**
     * token自动续租
     *
     * @param token token
     * @return boolean续租成功与失败
     */
    @RequestMapping(value = "/autopay", method = RequestMethod.POST)
    public boolean tokenAutoPay(@RequestParam("token") String token);

    /**
     * 根据token获取用户id
     *
     * @param token token
     * @return String类型id值
     */
    @RequestMapping(value = "/getUserId", method = RequestMethod.POST)
    public String getUserIdByToken(@RequestParam("token") String token);

    /**
     * 传入用户id,创建token
     *
     * @param userID 用户ID
     * @return String类型token值
     */
    @RequestMapping(value = "/createToken", method = RequestMethod.POST)
    public String createToken(@RequestParam("userID") String userID);

    /**
     * 移除token
     *
     * @param token token
     * @return 执行反馈
     */
    @RequestMapping(value = "/removeToken", method = RequestMethod.POST)
    public boolean removeToken(@RequestParam("token") String token);

    
}
