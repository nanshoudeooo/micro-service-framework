package com.bizdata.service.impl;

import com.bizdata.common.ResourceType;
import com.bizdata.dao.ResourceDao;
import com.bizdata.dao.RoleResourceRelationDao;
import com.bizdata.dao.UserRoleRelationDao;
import com.bizdata.entity.Resource;
import com.bizdata.entity.RoleResourceRelation;
import com.bizdata.entity.UserRoleRelation;
import com.bizdata.service.ResourceService;
import com.bizdata.vo.resource.CreateParamVO;
import com.bizdata.vo.resource.ReadByResourceIDResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by sdevil507 on 2017/4/19.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @Override
    public ReadByResourceIDResultVO findOne(String resourceID) {
        // 获取resource
        Resource resource = resourceDao.findOne(resourceID);
        ReadByResourceIDResultVO readByResourceIDResultVO = new ReadByResourceIDResultVO();
        BeanUtils.copyProperties(resource, readByResourceIDResultVO);
        // 获取父ID的资源名称
        String parentID = resource.getParent();
        if (Integer.valueOf(parentID) > 0) {
            Resource parentResource = resourceDao.findOne(resource.getParent());
            readByResourceIDResultVO.setParentName(parentResource.getName());
        }
        return readByResourceIDResultVO;
    }

    @Override
    public List<Resource> findAllByParentID(String parentID) {
        return resourceDao.findByParentOrderBySortNumAsc(parentID);
    }

    @Override
    @Transactional
    public boolean save(CreateParamVO createParamVO) {
        boolean state;
        try {
            Resource resource = new Resource();
            BeanUtils.copyProperties(createParamVO, resource);
            resourceDao.save(resource);
            if (ResourceType.MENU.equals(createParamVO.getResourceType()) && !createParamVO.isDir()) {
                //如果是菜单类型且不是目录,则自动生成CURD资源

                //查询
                Resource resourceView = new Resource();
                resourceView.setName("查询");
                resourceView.setParent(resource.getId());
                resourceView.setResourceType(ResourceType.ACTION);
                resourceView.setSortNum(1);
                resourceView.setPermission(resource.getPermission()+":view");
                resourceDao.save(resourceView);

                //新增
                Resource resourceCreate = new Resource();
                resourceCreate.setName("新增");
                resourceCreate.setParent(resource.getId());
                resourceCreate.setResourceType(ResourceType.ACTION);
                resourceCreate.setSortNum(1);
                resourceCreate.setPermission(resource.getPermission()+":create");
                resourceDao.save(resourceCreate);

                //修改
                Resource resourceUpdate = new Resource();
                resourceUpdate.setName("修改");
                resourceUpdate.setParent(resource.getId());
                resourceUpdate.setResourceType(ResourceType.ACTION);
                resourceUpdate.setSortNum(1);
                resourceUpdate.setPermission(resource.getPermission()+":update");
                resourceDao.save(resourceUpdate);

                //删除
                Resource resourceDelete = new Resource();
                resourceDelete.setName("删除");
                resourceDelete.setParent(resource.getId());
                resourceDelete.setResourceType(ResourceType.ACTION);
                resourceDelete.setSortNum(1);
                resourceDelete.setPermission(resource.getPermission()+":delete");
                resourceDao.save(resourceDelete);

            }
            state = true;
        } catch (Exception e) {
            logger.error("新增资源失败!", e);
            state = false;
        }
        return state;
    }
}
