package com.bizdata.service.impl;

import com.bizdata.common.ResourceType;
import com.bizdata.dao.ResourceDao;
import com.bizdata.dao.RoleResourceRelationDao;
import com.bizdata.dao.UserRoleRelationDao;
import com.bizdata.entity.Resource;
import com.bizdata.entity.RoleResourceRelation;
import com.bizdata.entity.UserRoleRelation;
import com.bizdata.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by sdevil507 on 2017/4/19.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private UserRoleRelationDao userRoleRelationDao;

    @Autowired
    private RoleResourceRelationDao roleResourceRelationDao;

    @Autowired
    private ResourceDao resourceDao;

    @Override
    @Transactional
    public Set<Resource> findMenusByUserID(String userID) {
        //根据用户ID查询出所有用户角色关联
        List<UserRoleRelation> userRoleRelations = userRoleRelationDao.findByUserID(userID);
        //封装角色id列表
        List<String> roleIds = new ArrayList<>();
        for (UserRoleRelation userRoleRelation : userRoleRelations) {
            roleIds.add(userRoleRelation.getRoleID());
        }
        //根据角色ID查询出资源列表
        List<Resource> menus = new ArrayList<>();
        for (String roleId : roleIds) {
            List<RoleResourceRelation> roleResourceRelations = roleResourceRelationDao.findAllByRoleID(roleId);
            for (RoleResourceRelation roleResourceRelation : roleResourceRelations) {
                //根据资源ID与资源类型取出菜单
                menus.add(resourceDao.findAllByIdAndResourceType(roleResourceRelation.getResourceID(), ResourceType.MENU));
            }
        }
        Collections.sort(menus);
        //去重
        Set<Resource> menuSet=new LinkedHashSet<>(menus);
        return menuSet;
    }
}
