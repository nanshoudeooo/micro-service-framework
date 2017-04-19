package com.bizdata.service.impl;

import com.bizdata.dao.TestDao;
import com.bizdata.entity.Test;
import com.bizdata.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by sdevil507 on 2017/4/14.
 */
@Service
@CacheConfig(cacheNames = "test")
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Cacheable
    @Override
    public Test findByName(String name) {
        return testDao.findByName(name);
    }

    @Cacheable
    @Override
    public Test findOne(String id) {
        return testDao.findOne(id);
    }
}
