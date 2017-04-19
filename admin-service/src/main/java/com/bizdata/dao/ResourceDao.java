package com.bizdata.dao;

import com.bizdata.common.ResourceType;
import com.bizdata.entity.Resource;
import com.bizdata.jpa.base.JpaBaseRepository;

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
    Resource findAllByIdAndResourceType(String id, ResourceType resourceType);
}
