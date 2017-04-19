package com.bizdata.entity;

import com.bizdata.jpa.base.JpaBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * 资源实体
 * <p>
 * Created by sdevil507 on 2017/4/16.
 */
@Entity
@Table(name = "admin_resource")
public class Resource extends JpaBaseEntity {

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
     * 是否是链接
     */
    private boolean isLink = false;

    /**
     * 权限字符串
     */
    @Column(nullable = false)
    private String permission = UUID.randomUUID().toString();

    /**
     * 资源图标
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
     * 是否展开
     */
    @Column(nullable = false)
    private Boolean expanded = true;

    /**
     * 加载
     */
    @Column(nullable = false)
    private Boolean loaded = true;

    /**
     * 层级
     */
    @Column(updatable = false, nullable = false)
    private int level;

    /**
     * 是否叶子节点
     */
    private Boolean isleaf = true;

    /**
     * 是否是根节点
     */
    private Boolean root = false;

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

    public boolean isLink() {
        return isLink;
    }

    public void setLink(boolean link) {
        isLink = link;
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

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public Boolean getLoaded() {
        return loaded;
    }

    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Boolean getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(Boolean isleaf) {
        this.isleaf = isleaf;
    }

    public Boolean getRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }
}
