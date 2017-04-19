package com.bizdata.service;

import com.bizdata.entity.Test;

/**
 * Created by sdevil507 on 2017/4/14.
 */
public interface TestService {

    Test findByName(String name);

    Test findOne(String id);
}
