package com.bizdata.controller.resource;

import com.bizdata.controller.resource.vo.in.InListByResourceTypeAndDirVO;
import com.bizdata.controller.resource.vo.in.InListResourceByUserIDAndResourceTypeVO;
import com.bizdata.controller.resource.vo.in.InSaveVO;
import com.bizdata.controller.resource.vo.in.InUpdateVO;
import com.bizdata.controller.resource.vo.out.OutResourceVO;
import com.bizdata.po.Resource;
import com.bizdata.req.IdentityUtil;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.ResourceService;
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
    @RequestMapping(value = "/resource/save", method = RequestMethod.POST)
    public ResultStateVO save(InSaveVO inSaveVO) {
        ResultStateVO resultStateVO;
        if (resourceService.save(inSaveVO)) {
            resultStateVO = ResultStateUtil.create(0, "资源新增成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "资源新增失败!");
        }
        return resultStateVO;
    }

    /**
     * 根据资源类型获取用户资源列表
     *
     * @param request 请求
     * @return ResultStateVO
     */
    @RequestMapping(value = "/resource/listResourceByUserIDAndResourceType", method = RequestMethod.POST)
    public ResultStateVO listResourceByUserIDAndResourceType(@Validated InListResourceByUserIDAndResourceTypeVO inListResourceByUserIDAndResourceTypeVO, HttpServletRequest request) {
        ResultStateVO resultStateVO;
        String userID = IdentityUtil.getUserID(request);
        try {
            Set<Resource> result = resourceService.listResourceByUserIDAndResourceType(userID, inListResourceByUserIDAndResourceTypeVO.getResourceType());
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
     * @param inListByResourceTypeAndDirVO 入参VO
     * @return ResultStateVO执行反馈
     */
    @RequestMapping(value = "/resource/listByResourceTypeAndDir", method = RequestMethod.POST)
    public ResultStateVO listByResourceTypeAndDir(@Validated InListByResourceTypeAndDirVO inListByResourceTypeAndDirVO) {
        ResultStateVO resultStateVO;
        try {
            List<Resource> resources = resourceService.listByResourceTypeAndDir(inListByResourceTypeAndDirVO.getResourceType(), inListByResourceTypeAndDirVO.isDir());
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
    @RequestMapping(value = "/resource/getByID", method = RequestMethod.POST)
    public ResultStateVO getByID(String resourceID) {
        ResultStateVO resultStateVO;
        try {
            OutResourceVO outResourceVO = resourceService.getByID(resourceID);
            resultStateVO = ResultStateUtil.create(0, "资源获取成功!", outResourceVO);
        } catch (Exception e) {
            logger.error("资源获取失败", e);
            resultStateVO = ResultStateUtil.create(1, "资源获取失败!");
        }
        return resultStateVO;
    }

    /**
     * 资源更新
     *
     * @param inUpdateVO 入参VO
     * @return ResultStateVO执行反馈
     */
    @RequestMapping(value = "/resource/update", method = RequestMethod.POST)
    public ResultStateVO update(InUpdateVO inUpdateVO) {
        ResultStateVO resultStateVO;
        if (resourceService.update(inUpdateVO)) {
            resultStateVO = ResultStateUtil.create(0, "资源更新成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "资源更新失败!");
        }
        return resultStateVO;
    }

    /**
     * 资源删除
     *
     * @param resourceID 资源ID
     * @return ResultStateVO执行反馈
     */
    @RequestMapping(value = "/resource/delete", method = RequestMethod.POST)
    public ResultStateVO delete(String resourceID) {
        return resourceService.delete(resourceID);
    }

    /**
     * 根据父ID获取子资源列表
     *
     * @param parentID 父ID
     * @return ResultStateVO执行反馈
     */
    @RequestMapping(value = "/resource/listByParentID", method = RequestMethod.POST)
    public ResultStateVO listByParentID(String parentID) {
        ResultStateVO resultStateVO;
        try {
            List<Resource> result = resourceService.listByParentID(parentID);
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
     * @return ResultStateVO
     */
    @RequestMapping(value = "/resource/listResourceType", method = RequestMethod.POST)
    public ResultStateVO listResourceType() {
        ResultStateVO resultStateVO;
        try {
            List<String> result = resourceService.listResourceType();
            resultStateVO = ResultStateUtil.create(0, "获取资源类型成功!", result);
        } catch (Exception e) {
            logger.error("获取资源类型失败!", e);
            resultStateVO = ResultStateUtil.create(1, "获取资源类型失败!");
        }
        return resultStateVO;
    }

    /**
     * 获取所有资源并标记被授权的资源
     *
     * @param roleID 角色ID
     * @return ResultStateVO
     */
    @RequestMapping(value = "/resource/listCheckedResourceByRoleIDAndResourceID", method = RequestMethod.POST)
    public ResultStateVO listCheckedResourceByRoleIDAndResourceID(String roleID, String resourceID) {
        return resourceService.listCheckedResourceByRoleIDAndResourceID(roleID, resourceID);
    }
}
