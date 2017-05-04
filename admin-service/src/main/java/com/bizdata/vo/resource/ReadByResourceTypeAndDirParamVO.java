package com.bizdata.vo.resource;

import com.bizdata.common.ResourceType;
import com.bizdata.vo.resource.valid.annotation.ResourceTypeValidAnnotation;

/**
 * 根据资源类型获取资源与是否是目录获取资源
 * <p>
 * Created by sdevil507 on 2017/5/4.
 */
public class ReadByResourceTypeAndDirParamVO {

    @ResourceTypeValidAnnotation
    private ResourceType resourceType;

    private boolean dir;

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public boolean isDir() {
        return dir;
    }

    public void setDir(boolean dir) {
        this.dir = dir;
    }
}
