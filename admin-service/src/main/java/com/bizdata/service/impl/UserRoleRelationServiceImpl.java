package com.bizdata.service.impl;

import com.bizdata.dao.UserRoleRelationDao;
import com.bizdata.entity.UserRoleRelation;
import com.bizdata.service.UserRoleRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public boolean buildUserAndRoleRelation(String userID, List<String> roleIds) {
        boolean state = false;
        try {
            userRoleRelationDao.deleteByUserID(userID);
            for (String roleId : roleIds) {
                UserRoleRelation userRoleRelation = new UserRoleRelation();
                userRoleRelation.setUserID(userID);
                userRoleRelation.setRoleID(roleId);
                userRoleRelationDao.save(userRoleRelation);
            }
            state = true;
        } catch (Exception e) {
            logger.error("用户角色关系建立失败", e);
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

    @Override
    public List<UserRoleRelation> findByUserID(String userID) {
        return userRoleRelationDao.findByUserID(userID);
    }
}
