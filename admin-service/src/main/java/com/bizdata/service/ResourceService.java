package com.bizdata.service;

import com.bizdata.common.ResourceType;
import com.bizdata.entity.Resource;

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
     * @param userID 用户ID
     * @param resourceType
     * @return Set<Resource>
     */
    Set<Resource> findResourceByUserIDAndResourceType(String userID, ResourceType resourceType);

    /**
     * 根据资源类型获取所有资源
     *
     * @param resourceType 资源类型
     * @param dir 是否是目录
     * @return List<Resource>
     */
    List<Resource> findAllByResourceTypeAndDir(ResourceType resourceType,boolean dir);

    /**
     * 获取资源类型
     *
     * @return
     */
    List<String> getResourceType();

    /**
     * 获取用户ID的可访问URL列表
     *
     * @param userID 用户ID
     * @return List<String>
     */
    List<String> findAuthUrl(String userID);

    /**
     * 获取全部资源url列表
     *
     * @return
     */
    List<String> findAllResourceUrl();
}
