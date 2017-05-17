package com.bizdata.service.impl;

import com.bizdata.dao.RoleResourceRelationDao;
import com.bizdata.po.RoleResourceRelation;
import com.bizdata.service.RoleResourceRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色资源关系Service实现
 * <p>
 * Created by sdevil507 on 2017/4/16.
 */
@Service
public class RoleResourceRelationServiceImpl implements RoleResourceRelationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleResourceRelationDao roleResourceRelationDao;

    @Override
    @Transactional
    public boolean build(String roleID, String[] resourceIDs) {
        boolean state = false;
        try {
            //先删除关系
            roleResourceRelationDao.deleteByRoleID(roleID);
            //建立关系
            for (String resourceID : resourceIDs) {
                RoleResourceRelation roleResourceRelation = new RoleResourceRelation();
                roleResourceRelation.setRoleID(roleID);
                roleResourceRelation.setResourceID(resourceID);
                roleResourceRelationDao.save(roleResourceRelation);
            }
            state = true;
        } catch (Exception e) {
            logger.error("角色资源关系绑定失败!", e);
            state = false;
        }
        return state;
    }
}
