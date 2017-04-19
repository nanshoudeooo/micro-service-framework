package com.bizdata.controller;

import com.bizdata.entity.Resource;
import com.bizdata.req.IdentityUtil;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.ResourceService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 资源Controller
 *
 * Created by sdevil507 on 2017/4/19.
 */
@RestController
public class ResourceController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceService resourceService;


    /**
     * 获取用户菜单
     *
     * @param request
     * @return ResultStateVO
     */
    @RequestMapping(value = "/menu/read",method = RequestMethod.POST)
    public ResultStateVO read(HttpServletRequest request){
        ResultStateVO resultStateVO=null;
        String userID= IdentityUtil.getUserID(request);
        try {
            Set<Resource> result=resourceService.findMenusByUserID(userID);
            resultStateVO=ResultStateUtil.create(0,"菜单获取成功!",result);
        }catch (Exception e){
            logger.error("菜单获取失败!",e);
            resultStateVO=ResultStateUtil.create(1,"菜单获取失败!");
        }
        return resultStateVO;
    }
}
