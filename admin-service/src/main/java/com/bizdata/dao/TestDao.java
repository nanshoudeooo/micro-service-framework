package com.bizdata.dao;

import com.bizdata.entity.Test;
import com.bizdata.jpa.base.JpaBaseRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

/**
 * Created by sdevil507 on 2017/4/14.
 */
public interface TestDao extends JpaBaseRepository<Test, String> {

    Test findByName(String name);
}
