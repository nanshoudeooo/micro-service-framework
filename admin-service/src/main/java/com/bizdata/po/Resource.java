package com.bizdata.po;

import com.bizdata.common.MenuType;
import com.bizdata.common.ResourceType;
import me.sdevil507.base.JpaUUIDBaseEntity;

import javax.persistence.*;

/**
 * 资源实体
 * <p>
 * Created by sdevil507 on 2017/4/16.
 */
@Entity
@Table(name = "admin_resource")
public class Resource extends JpaUUIDBaseEntity implements Comparable<Resource> {

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

    public boolean isDir() {
        return dir;
    }

    public void setDir(boolean dir) {
        this.dir = dir;
    }

    @Override
    public int compareTo(Resource o) {
        return this.getSortNum().compareTo(o.getSortNum());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (builtIn != resource.builtIn) return false;
        if (dir != resource.dir) return false;
        if (name != null ? !name.equals(resource.name) : resource.name != null) return false;
        if (url != null ? !url.equals(resource.url) : resource.url != null) return false;
        if (permission != null ? !permission.equals(resource.permission) : resource.permission != null) return false;
        if (icon != null ? !icon.equals(resource.icon) : resource.icon != null) return false;
        if (sortNum != null ? !sortNum.equals(resource.sortNum) : resource.sortNum != null) return false;
        if (parent != null ? !parent.equals(resource.parent) : resource.parent != null) return false;
        if (menuType != resource.menuType) return false;
        return resourceType == resource.resourceType;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (permission != null ? permission.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (builtIn ? 1 : 0);
        result = 31 * result + (sortNum != null ? sortNum.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (menuType != null ? menuType.hashCode() : 0);
        result = 31 * result + (resourceType != null ? resourceType.hashCode() : 0);
        result = 31 * result + (dir ? 1 : 0);
        return result;
    }
}
