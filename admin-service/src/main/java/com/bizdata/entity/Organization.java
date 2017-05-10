package com.bizdata.entity;

import com.bizdata.jpa.base.JpaBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 组织机构
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
@Entity
@Table(name = "admin_organization")
public class Organization extends JpaBaseEntity {
    /**
     * 组织机构名称
     */
    @Column(nullable = false,unique = true)
    private String name;

    /**
     * 父id
     */
    @Column(updatable = false)
    private String parent = "";

    private Date createTime = new Date();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
