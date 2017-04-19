package com.bizdata.entity;

import com.bizdata.common.MenuType;
import com.bizdata.common.ResourceType;
import com.bizdata.jpa.base.JpaBaseEntity;

import javax.persistence.*;

/**
 * 资源实体
 * <p>
 * Created by sdevil507 on 2017/4/16.
 */
@Entity
@Table(name = "admin_resource")
public class Resource extends JpaBaseEntity implements Comparable<Resource> {

    /**
     * 资源名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 权限字符串
     */
    @Column(nullable = false)
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
    @Column(nullable = false)
    private Integer sortNum;

    /**
     * 父id
     */
    @Column(updatable = false)
    private String parent = "";

    /**
     * 针对于菜单类型时,确定是顶部菜单还是左侧菜单
     */
    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    /**
     * 资源类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResourceType resourceType;

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

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }

    @Override
    public int compareTo(Resource o) {
        return this.getSortNum().compareTo(o.getSortNum());
    }
}
