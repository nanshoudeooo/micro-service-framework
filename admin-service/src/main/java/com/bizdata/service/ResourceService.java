package com.bizdata.service;

import com.bizdata.common.ResourceType;
import com.bizdata.po.Resource;
import com.bizdata.result.ResultStateVO;
import com.bizdata.controller.resource.vo.in.InSaveVO;
import com.bizdata.controller.resource.vo.out.OutResourceVO;
import com.bizdata.controller.resource.vo.in.InUpdateVO;

import java.util.List;
import java.util.Set;

/**
 * 资源Service
 * <p>
 * Created by sdevil507 on 2017/4/19.
 */
public interface ResourceService {

    /**
     * 根据用户ID查询出菜单
     *
     * @param userID       用户ID
     * @param resourceType 资源类型
     * @return Set<Resource>
     */
    Set<Resource> listResourceByUserIDAndResourceType(String userID, ResourceType resourceType);

    /**
     * 根据资源类型获取所有资源
     *
     * @param resourceType 资源类型
     * @param dir          是否是目录
     * @return List<Resource>
     */
    List<Resource> listByResourceTypeAndDir(ResourceType resourceType, boolean dir);

    /**
     * 获取资源类型
     *
     * @return List<String>
     */
    List<String> listResourceType();

    /**
     * 获取用户ID的可访问URL列表
     *
     * @param userID 用户ID
     * @return List<String>
     */
    List<String> listAuthUrl(String userID);

    /**
     * 获取全部资源url列表
     *
     * @return
     */
    List<String> listResourceUrl();

    /**
     * 根据资源ID获取Resource
     *
     * @param resourceID 资源ID
     * @return OutResourceVO
     */
    OutResourceVO getByID(String resourceID);

    /**
     * 根据父ID获取资源列表
     *
     * @param parentID 父ID
     * @return List<Resource>
     */
    List<Resource> listByParentID(String parentID);

    /**
     * 资源新增
     *
     * @param inSaveVO 入参VO
     * @return boolean
     */
    boolean save(InSaveVO inSaveVO);

    /**
     * 资源更新
     *
     * @param inUpdateVO 入参VO
     * @return boolean
     */
    boolean update(InUpdateVO inUpdateVO);

    /**
     * 资源删除
     *
     * @param resourceID 资源ID
     * @return ResultStateVO
     */
    ResultStateVO delete(String resourceID);

    /**
     * 根据roleID获取该角色已经选中的资源
     *
     * @param roleID     角色ID
     * @param resourceID 资源ID
     * @return ResultStateVO
     */
    ResultStateVO listCheckedResourceByRoleIDAndResourceID(String roleID, String resourceID);

}
