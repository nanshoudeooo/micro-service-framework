package com.bizdata.controller.resource.vo.out;

import com.bizdata.common.ResourceType;

/**
 * 资源实体包含是否跟当前实体绑定checked标签VO
 * <p>
 * Created by sdevil507 on 2017/5/16.
 */
public class OutResourceIncludeCheckedVO {
    /**
     * 资源ID
     */
    private String id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 是否为内置
     */
    private boolean builtIn = false;

    /**
     * 父id
     */
    private String parent = "";

    /**
     * 资源类型
     */
    private ResourceType resourceType;

    /**
     * 是否是目录
     */
    private boolean dir = false;

    /**
     * 是否选中
     */
    private boolean checked = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(boolean builtIn) {
        this.builtIn = builtIn;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
