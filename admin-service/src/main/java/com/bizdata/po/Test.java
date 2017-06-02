package com.bizdata.po;

import me.sdevil507.base.JpaUUIDBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by sdevil507 on 2017/4/14.
 */
@Entity
@Table(name = "test")
public class Test extends JpaUUIDBaseEntity{

    @Column
    private String name;

    @Column
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
