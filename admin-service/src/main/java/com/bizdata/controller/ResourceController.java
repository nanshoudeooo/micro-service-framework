package com.bizdata.controller;

import com.bizdata.entity.Resource;
import com.bizdata.req.IdentityUtil;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.ResourceService;
import com.bizdata.vo.resource.*;
import com.bizdata.vo.resource.valid.update.ValidGroupUpdate;
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
     * 新增资源
     *
     * @return ResultStateVO
     */
    @RequestMapping(value = "/resource/create", method = RequestMethod.POST)
    public ResultStateVO create(CreateParamVO createParamVO) {
        ResultStateVO resultStateVO;
        if (resourceService.save(createParamVO)) {
            resultStateVO = ResultStateUtil.create(0, "资源新增成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "资源新增失败!");
        }
        return resultStateVO;
    }

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
     * 根据资源类型与是否是目录获取资源列表
     *
     * @param readByResourceTypeAndDirParamVO 入参VO
     * @return ResultStateVO执行反馈
     */
    @RequestMapping(value = "/resource/readByResourceTypeAndDir", method = RequestMethod.POST)
    public ResultStateVO readByResourceTypeAndDir(@Validated ReadByResourceTypeAndDirParamVO readByResourceTypeAndDirParamVO) {
        ResultStateVO resultStateVO;
        try {
            List<Resource> resources = resourceService.findAllByResourceTypeAndDir(readByResourceTypeAndDirParamVO.getResourceType(), readByResourceTypeAndDirParamVO.isDir());
            resultStateVO = ResultStateUtil.create(0, "资源获取成功!", resources);
        } catch (Exception e) {
            logger.error("资源获取失败", e);
            resultStateVO = ResultStateUtil.create(1, "资源获取失败!");
        }
        return resultStateVO;
    }

    /**
     * 根据资源ID获取资源详细信息
     *
     * @param resourceID 资源ID
     * @return ResultStateVO执行反馈
     */
    @RequestMapping(value = "/resource/readByResourceID", method = RequestMethod.POST)
    public ResultStateVO readByResourceID(String resourceID) {
        ResultStateVO resultStateVO;
        try {
            ReadByResourceIDResultVO readByResourceIDResultVO = resourceService.findOne(resourceID);
            resultStateVO = ResultStateUtil.create(0, "资源获取成功!", readByResourceIDResultVO);
        } catch (Exception e) {
            logger.error("资源获取失败", e);
            resultStateVO = ResultStateUtil.create(1, "资源获取失败!");
        }
        return resultStateVO;
    }

    /**
     * 资源更新
     *
     * @param updateParamVO 入参VO
     * @return ResultStateVO执行反馈
     */
    @RequestMapping(value = "/resource/update", method = RequestMethod.POST)
    public ResultStateVO update(@Validated(ValidGroupUpdate.class) UpdateParamVO updateParamVO) {
        ResultStateVO resultStateVO;
        if (resourceService.update(updateParamVO)) {
            resultStateVO = ResultStateUtil.create(0, "资源更新成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "资源更新失败!");
        }
        return resultStateVO;
    }
    
    /**
     * 根据父ID获取子资源列表
     *
     * @param parentID 父ID
     * @return ResultStateVO执行反馈
     */
    @RequestMapping(value = "/resource/findAllByParentID", method = RequestMethod.POST)
    public ResultStateVO findAllByParentID(String parentID) {
        ResultStateVO resultStateVO;
        try {
            List<Resource> result = resourceService.findAllByParentID(parentID);
            resultStateVO = ResultStateUtil.create(0, "资源获取成功!", result);
        } catch (Exception e) {
            logger.error("资源获取失败", e);
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

    /**
     * 获取登录用户授权访问的URL列表
     *
     * @return
     */
    @RequestMapping(value = "/getAuthResourceUrl", method = RequestMethod.POST)
    public List<String> getAuthResourceUrl(String userID) {
        return resourceService.findAuthUrl(userID);
    }

    /**
     * 获取全部资源配置url列表
     *
     * @return
     */
    @RequestMapping(value = "/getAllResourceUrl", method = RequestMethod.POST)
    List<String> getAllResourceUrl() {
        return resourceService.findAllResourceUrl();
    }
}
