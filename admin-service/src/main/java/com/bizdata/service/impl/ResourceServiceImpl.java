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
    public Set<Resource> findResourceByUserIDAndResourceType(String userID, ResourceType resourceType) {
        //根据用户ID查询出所有用户角色关联
        List<UserRoleRelation> userRoleRelations = userRoleRelationDao.findByUserID(userID);
        //封装角色id列表
        List<String> roleIds = new ArrayList<>();
        for (UserRoleRelation userRoleRelation : userRoleRelations) {
            roleIds.add(userRoleRelation.getRoleID());
        }
        //根据角色ID查询出资源列表
        List<Resource> resources = new ArrayList<>();
        for (String roleId : roleIds) {
            List<RoleResourceRelation> roleResourceRelations = roleResourceRelationDao.findAllByRoleID(roleId);
            for (RoleResourceRelation roleResourceRelation : roleResourceRelations) {
                //根据资源ID与资源类型取出菜单
                Resource resource = resourceDao.findByIdAndResourceType(roleResourceRelation.getResourceID(), resourceType);
                if (null != resource) {
                    resources.add(resource);
                }
            }
        }
        Collections.sort(resources);
        //去重
        Set<Resource> resourceSet = new LinkedHashSet<>(resources);
        return resourceSet;
    }

    @Override
    public List<Resource> findAllByResourceTypeAndDir(ResourceType resourceType, boolean dir) {
        if (false == dir) {
            return resourceDao.findByResourceType(resourceType);
        }
        return resourceDao.findByResourceTypeAndDir(resourceType, dir);
    }

    @Override
    public List<String> getResourceType() {
        List<String> resourceTypes = new ArrayList<>();
        for (ResourceType r : ResourceType.values()) {
            resourceTypes.add(r.name());
        }
        return resourceTypes;
    }

    @Override
    @Transactional
    public List<String> findAuthUrl(String userID) {
        //根据用户ID查询出所有用户角色关联
        List<UserRoleRelation> userRoleRelations = userRoleRelationDao.findByUserID(userID);
        //封装角色id列表
        List<String> roleIds = new ArrayList<>();
        for (UserRoleRelation userRoleRelation : userRoleRelations) {
            roleIds.add(userRoleRelation.getRoleID());
        }
        //根据角色ID查询出资源列表
        List<Resource> resources = new ArrayList<>();
        for (String roleId : roleIds) {
            List<RoleResourceRelation> roleResourceRelations = roleResourceRelationDao.findAllByRoleID(roleId);
            for (RoleResourceRelation roleResourceRelation : roleResourceRelations) {
                //根据资源ID与资源类型取出菜单
                Resource resource = resourceDao.findOne(roleResourceRelation.getResourceID());
                if (null != resource) {
                    resources.add(resource);
                }
            }
        }
        Collections.sort(resources);
        //去重
        Set<Resource> resourceSet = new LinkedHashSet<>(resources);
        List<String> urls = new ArrayList<>();
        for (Resource resource : resourceSet) {
            urls.add(resource.getUrl());
        }
        return urls;
    }

    @Override
    public List<String> findAllResourceUrl() {
        List<Resource> resources = resourceDao.findAll();
        List<String> resourceUrls = new ArrayList<>();
        for (Resource resource : resources) {
            resourceUrls.add(resource.getUrl());
        }
        return resourceUrls;
    }
}
