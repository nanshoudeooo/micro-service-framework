package com.bizdata.vo.resource;

import com.bizdata.common.MenuType;
import com.bizdata.common.ResourceType;

/**
 * 读取详细资源信息VO
 * <p>
 * Created by sdevil507 on 2017/5/4.
 */
public class ReadByResourceIDResultVO {

    /**
     * 资源ID
     */
    private String id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 权限字符串
     */
    private String permission;

    /**
     * 资源icon
     */
    private String icon;

    /**
     * 是否为内置
     */
    private boolean builtIn = false;

    /**
     * 排序号
     */
    private Integer sortNum;

    /**
     * 父id
     */
    private String parent = "";

    /**
     * 父菜单名称
     */
    private String parentName="";

    /**
     * 针对于菜单类型时,确定是顶部菜单还是左侧菜单
     */
    private MenuType menuType;

    /**
     * 资源类型
     */
    private ResourceType resourceType;

    /**
     * 是否是目录
     */
    private boolean dir = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(boolean builtIn) {
        this.builtIn = builtIn;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
