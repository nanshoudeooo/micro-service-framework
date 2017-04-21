package com.bizdata.vo.resource;

import com.bizdata.common.ResourceType;
import com.bizdata.vo.resource.valid.annotation.ResourceTypeValidAnnotation;

/**
 * 根据资源类型获取资源
 * <p>
 * Created by sdevil507 on 2017/4/20.
 */
public class ResourceReadByResourceTypeParamVO {

    @ResourceTypeValidAnnotation
    private ResourceType resourceType;

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
