package com.bizdata.po;

import com.bizdata.jpa.base.JpaBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 数据初始化表
 *
 * @author sdevil507
 * @version 1.0
 */
@Entity
@Table(name = "admin_init")
public class Init extends JpaBaseEntity {
    /**
     * 是否已经初始化
     */
    @Column(nullable = false)
    private boolean state = false;

    @Column(nullable = false)
    private Date date = new Date();

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
