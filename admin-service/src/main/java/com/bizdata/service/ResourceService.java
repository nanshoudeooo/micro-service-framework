package com.bizdata.service;

import com.bizdata.entity.Resource;

import java.util.List;
import java.util.Set;

/**
 * 资源Service
 * <p>
 * Created by sdevil507 on 2017/4/19.
 */
public interface ResourceService {

    /**
     * 根据用户ID查询出菜单
     *
     * @param userID 用户ID
     * @return List<Resource>
     */
    Set<Resource> findMenusByUserID(String userID);
}
