package com.bizdata.dao;

import com.bizdata.po.Test;
import com.bizdata.jpa.base.JpaBaseRepository;

/**
 * 测试Dao
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
public interface TestDao extends JpaBaseRepository<Test, String> {

    Test findByName(String name);
}
