package com.bizdata.provider;

import com.bizdata.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 提供资源相关微服务接口实现
 * <p>
 * Created by sdevil507 on 2017/5/12.
 */
@RestController
public class ResourceProvider {

    @Autowired
    private ResourceService resourceService;

    /**
     * 获取登录用户授权访问的URL列表
     *
     * @return List<String>
     */
    @RequestMapping(value = "/listAuthUrl", method = RequestMethod.POST)
    public List<String> listAuthUrl(String userID) {
        return resourceService.listAuthUrl(userID);
    }

    /**
     * 获取全部资源配置url列表
     *
     * @return List<String>
     */
    @RequestMapping(value = "/listResourceUrl", method = RequestMethod.POST)
    public List<String> listResourceUrl() {
        return resourceService.listResourceUrl();
    }
}
