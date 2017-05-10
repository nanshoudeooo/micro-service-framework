package com.bizdata.service.impl;

import com.bizdata.dao.UserRoleRelationDao;
import com.bizdata.po.UserRoleRelation;
import com.bizdata.service.UserRoleRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户角色关系Service实现
 * <p>
 * Created by sdevil507 on 2017/4/16.
 */
@Service
public class UserRoleRelationServiceImpl implements UserRoleRelationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRoleRelationDao userRoleRelationDao;

    @Override
    @Transactional
    public boolean build(String roleID, String[] userIDs) {
        boolean state;
        try {
            //根据roleID删除原有关系
            userRoleRelationDao.deleteByRoleID(roleID);
            //循环增加现有关系
            for (String userID : userIDs) {
                UserRoleRelation userRoleRelation = new UserRoleRelation();
                userRoleRelation.setUserID(userID);
                userRoleRelation.setRoleID(roleID);
                userRoleRelationDao.save(userRoleRelation);
            }
            state = true;
        } catch (Exception e) {
            logger.error("角色绑定失败!", e);
            state = false;
        }
        return state;
    }

    @Override
    public boolean deleteByUserID(String userID) {
        boolean state = false;
        try {
            userRoleRelationDao.deleteByUserID(userID);
            state = true;
        } catch (Exception e) {
            logger.error("用户角色关系删除失败!", e);
        }
        return state;
    }
}
