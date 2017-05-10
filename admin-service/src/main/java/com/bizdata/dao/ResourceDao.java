package com.bizdata.dao;

import com.bizdata.common.ResourceType;
import com.bizdata.po.Resource;
import com.bizdata.jpa.base.JpaBaseRepository;

import java.util.List;

/**
 * 资源Dao
 * <p>
 * Created by sdevil507 on 2017/4/19.
 */
public interface ResourceDao extends JpaBaseRepository<Resource, String> {
    /**
     * 根据资源类型获取资源
     *
     * @param resourceType 资源类型
     * @return List<Resource>
     */
    Resource findByIdAndResourceType(String id, ResourceType resourceType);

    /**
     * 根据资源类型和是否是目录获取资源
     *
     * @param resourceType 资源类型
     * @param dir          是否是目录
     * @return List<Resource>
     */
    List<Resource> findByResourceTypeAndDir(ResourceType resourceType, boolean dir);

    /**
     * 根据资源类型获取资源列表
     *
     * @param resourceType 资源类型
     * @return List<Resource>
     */
    List<Resource> findByResourceType(ResourceType resourceType);

    /**
     * 根据父ID获取子资源列表
     *
     * @param parent 父ID
     * @return List<Resource>
     */
    List<Resource> findByParentOrderBySortNumAsc(String parent);
}
