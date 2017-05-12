package com.bizdata.controller.resource.vo.in;

import com.bizdata.common.MenuType;
import com.bizdata.common.ResourceType;
import com.bizdata.controller.resource.vo.in.valid.field.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * 更新操作VO类
 * <p>
 * Created by sdevil507 on 2017/5/8.
 */
public class InUpdateVO {

    @NotBlank(message = "资源ID不可为空!", groups = ValidFieldID.class)
    private String id;

    /**
     * 资源名称
     */
    @NotBlank(message = "资源名称不可为空!", groups = ValidFieldName.class)
    private String name;

    /**
     * 资源路径
     */
    @NotBlank(message = "资源路径不可为空!", groups = ValidFieldUrl.class)
    private String url;

    /**
     * 权限字符串
     */
    @NotBlank(message = "权限字符串不可为空!", groups = ValidFieldPermission.class)
    private String permission;

    /**
     * 资源icon
     */
    private String icon;

    /**
     * 排序号
     */
    @Min(value = 0, message = "排序号必须大于0", groups = ValidFieldSortNum.class)
    private Integer sortNum;

    /**
     * 父id
     */
    @NotBlank(message = "父ID必须传递", groups = ValidFieldParent.class)
    private String parent = "";

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
    @NotBlank(message = "是否是目录类型必须传递", groups = ValidFieldDir.class)
    private boolean dir = false;

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
}
