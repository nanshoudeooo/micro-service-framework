package com.bizdata.controller;

import com.bizdata.entity.Resource;
import com.bizdata.req.IdentityUtil;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.ResourceService;
import com.bizdata.vo.resource.ResourceReadByResourceTypeParamVO;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 资源Controller
 * <p>
 * Created by sdevil507 on 2017/4/19.
 */
@RestController
public class ResourceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceService resourceService;

    /**
     * 根据资源类型获取用户资源列表
     *
     * @param request
     * @return ResultStateVO
     */
    @RequestMapping(value = "/resource/readByResourceType", method = RequestMethod.POST)
    public ResultStateVO readByResourceType(@Validated ResourceReadByResourceTypeParamVO resourceReadByResourceTypeParamVO, HttpServletRequest request) {
        ResultStateVO resultStateVO;
        String userID = IdentityUtil.getUserID(request);
        try {
            Set<Resource> result = resourceService.findResourceByUserIDAndResourceType(userID, resourceReadByResourceTypeParamVO.getResourceType());
            resultStateVO = ResultStateUtil.create(0, "资源获取成功!", result);
        } catch (Exception e) {
            logger.error("资源获取失败!", e);
            resultStateVO = ResultStateUtil.create(1, "资源获取失败!");
        }
        return resultStateVO;
    }

    /**
     * 获取资源类型
     *
     * @return
     */
    @RequestMapping(value = "/resource/getResourceType", method = RequestMethod.POST)
    public ResultStateVO getResourceType() {
        ResultStateVO resultStateVO;
        try {
            List<String> result = resourceService.getResourceType();
            resultStateVO = ResultStateUtil.create(0, "获取资源类型成功!", result);
        } catch (Exception e) {
            logger.error("获取资源类型失败!", e);
            resultStateVO = ResultStateUtil.create(1, "获取资源类型失败!");
        }
        return resultStateVO;
    }
}
