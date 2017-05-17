package com.bizdata.service.impl;

import com.bizdata.common.ResourceType;
import com.bizdata.controller.resource.vo.out.OutResourceIncludeCheckedVO;
import com.bizdata.dao.ResourceDao;
import com.bizdata.dao.RoleResourceRelationDao;
import com.bizdata.dao.UserRoleRelationDao;
import com.bizdata.po.Organization;
import com.bizdata.po.Resource;
import com.bizdata.po.RoleResourceRelation;
import com.bizdata.po.UserRoleRelation;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.ResourceService;
import com.bizdata.controller.resource.vo.in.InSaveVO;
import com.bizdata.controller.resource.vo.out.OutResourceVO;
import com.bizdata.controller.resource.vo.in.InUpdateVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 资源Server实现
 * <p>
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
    public Set<Resource> listResourceByUserIDAndResourceType(String userID, ResourceType resourceType) {
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
    public List<Resource> listByResourceTypeAndDir(ResourceType resourceType, boolean dir) {
        if (!dir) {
            return resourceDao.findByResourceType(resourceType);
        }
        return resourceDao.findByResourceTypeAndDir(resourceType, dir);
    }

    @Override
    public List<String> listResourceType() {
        List<String> resourceTypes = new ArrayList<>();
        for (ResourceType r : ResourceType.values()) {
            resourceTypes.add(r.name());
        }
        return resourceTypes;
    }

    @Override
    @Transactional
    public List<String> listAuthUrl(String userID) {
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
            if (null != resource.getUrl()) {
                urls.add(resource.getUrl());
            }
        }
        return urls;
    }

    @Override
    public List<String> listResourceUrl() {
        List<Resource> resources = resourceDao.findAll();
        List<String> resourceUrls = new ArrayList<>();
        for (Resource resource : resources) {
            if (null != resource.getUrl()) {
                resourceUrls.add(resource.getUrl());
            }
        }
        return resourceUrls;
    }

    @Override
    public OutResourceVO getByID(String resourceID) {
        // 获取resource
        Resource resource = resourceDao.findOne(resourceID);
        OutResourceVO outResourceVO = new OutResourceVO();
        BeanUtils.copyProperties(resource, outResourceVO);
        // 获取父ID的资源名称
        String parentID = resource.getParent();
        if (!parentID.equals("0")) {
            Resource parentResource = resourceDao.findOne(resource.getParent());
            outResourceVO.setParentName(parentResource.getName());
        }
        return outResourceVO;
    }

    @Override
    public List<Resource> listByParentID(String parentID) {
        return resourceDao.findByParentOrderBySortNumAsc(parentID);
    }

    @Override
    @Transactional
    public boolean save(InSaveVO inSaveVO) {
        boolean state;
        try {
            Resource resource = new Resource();
            BeanUtils.copyProperties(inSaveVO, resource);
            resourceDao.save(resource);
            if (ResourceType.MENU.equals(inSaveVO.getResourceType()) && !inSaveVO.isDir()) {
                //如果是菜单类型且不是目录,则自动生成CURD资源

                //查询
                Resource resourceView = new Resource();
                resourceView.setName("查询");
                resourceView.setParent(resource.getId());
                resourceView.setResourceType(ResourceType.ACTION);
                resourceView.setSortNum(1);
                resourceView.setPermission(resource.getPermission() + ":view");
                resourceDao.save(resourceView);

                //新增
                Resource resourceCreate = new Resource();
                resourceCreate.setName("新增");
                resourceCreate.setParent(resource.getId());
                resourceCreate.setResourceType(ResourceType.ACTION);
                resourceCreate.setSortNum(1);
                resourceCreate.setPermission(resource.getPermission() + ":create");
                resourceDao.save(resourceCreate);

                //修改
                Resource resourceUpdate = new Resource();
                resourceUpdate.setName("修改");
                resourceUpdate.setParent(resource.getId());
                resourceUpdate.setResourceType(ResourceType.ACTION);
                resourceUpdate.setSortNum(1);
                resourceUpdate.setPermission(resource.getPermission() + ":update");
                resourceDao.save(resourceUpdate);

                //删除
                Resource resourceDelete = new Resource();
                resourceDelete.setName("删除");
                resourceDelete.setParent(resource.getId());
                resourceDelete.setResourceType(ResourceType.ACTION);
                resourceDelete.setSortNum(1);
                resourceDelete.setPermission(resource.getPermission() + ":delete");
                resourceDao.save(resourceDelete);

            }
            state = true;
        } catch (Exception e) {
            logger.error("新增资源失败!", e);
            state = false;
        }
        return state;
    }

    @Override
    public boolean update(InUpdateVO inUpdateVO) {
        boolean state;
        try {
            Resource resource = new Resource();
            BeanUtils.copyProperties(inUpdateVO, resource);
            resourceDao.save(resource);
            state = true;
        } catch (Exception e) {
            logger.error("资源更新失败", e);
            state = false;
        }
        return state;
    }

    @Override
    public ResultStateVO delete(String resourceID) {
        ResultStateVO resultStateVO;
        try {
            //判断是否包含子级节点,如果包含不可删除
            if (hasChildren(resourceID)) {
                resultStateVO = ResultStateUtil.create(1, "当前节点包含子节点,不可删除!");
            } else if (hasRoleResourceRelation(resourceID)) {
                //判断是否和角色关联,如果有关联,则提示不可删除
                resultStateVO = ResultStateUtil.create(2, "当前资源与角色关联,请先解除关联,执行删除!");
            } else {
                resourceDao.delete(resourceID);
                resultStateVO = ResultStateUtil.create(0, "当前资源删除成功!");
            }
        } catch (Exception e) {
            logger.error("删除资源失败", e);
            resultStateVO = ResultStateUtil.create(3, "删除资源失败");
        }
        return resultStateVO;
    }

    @Override
    @Transactional
    public ResultStateVO listCheckedResourceByRoleIDAndResourceID(String roleID, String resourceID) {
        ResultStateVO resultStateVO;
        List<OutResourceIncludeCheckedVO> out;
        try {
            //获取所有资源
            List<Resource> resources = resourceDao.findAll();
            //根据角色资源关系获取选中的资源
            List<RoleResourceRelation> roleResourceRelations = roleResourceRelationDao.findAllByRoleID(roleID);
            //封装返回数据
            List<OutResourceIncludeCheckedVO> outResourceIncludeCheckedVOs = new ArrayList<>();
            for (Resource resource : resources) {
                boolean checked = false;
                for (RoleResourceRelation roleResourceRelation : roleResourceRelations) {
                    if (resource.getId().equals(roleResourceRelation.getResourceID())) {
                        checked = true;
                    }
                }
                OutResourceIncludeCheckedVO outResourceIncludeCheckedVO = new OutResourceIncludeCheckedVO();
                BeanUtils.copyProperties(resource, outResourceIncludeCheckedVO);
                outResourceIncludeCheckedVO.setChecked(checked);
                outResourceIncludeCheckedVOs.add(outResourceIncludeCheckedVO);
            }
            if (StringUtils.isEmpty(resourceID)) {
                out = outResourceIncludeCheckedVOs;
            } else {
                List<OutResourceIncludeCheckedVO> conditionResourceVOs = new ArrayList<>();
                //如果存在resourceID条件
                List<String> ids = listConditionResource(resourceID);
                for (OutResourceIncludeCheckedVO outResourceIncludeCheckedVO : outResourceIncludeCheckedVOs) {
                    for (String id : ids) {
                        if (outResourceIncludeCheckedVO.getId().equals(id)) {
                            conditionResourceVOs.add(outResourceIncludeCheckedVO);
                        }
                    }
                }
                out = conditionResourceVOs;
            }
            resultStateVO = ResultStateUtil.create(0, "根据RoleID获取资源信息成功!", out);
        } catch (Exception e) {
            logger.error("根据RoleID获取资源失败!", e);
            resultStateVO = ResultStateUtil.create(1, "根据RoleID获取资源失败!");
        }
        return resultStateVO;
    }

    /**
     * 根据复杂条件获取resourceID列表
     *
     * @param resourceID 资源ID
     * @return List<String>
     */
    private List<String> listConditionResource(String resourceID) {
        List<Resource> resources = new ArrayList<>();
        Resource resource = resourceDao.findOne(resourceID);
        if (resource.isDir()) {
            //如果是目录,则返回该id下所有子资源列表
            resources.add(resource);
            resources.addAll(getNextLevel(resources));
            resources.remove(resource);
        } else {
            //如果不是目录,向上获取父级,向下获取子资源列表
            resources.add(resource);
            resources.addAll(getNextLevel(resources));
            resources.addAll(getPreviousLevel(resource));
            resources.remove(resource);
        }
        List<String> ids = new ArrayList<>();
        for (Resource tempResource : resources) {
            ids.add(tempResource.getId());
        }
        return ids;
    }

    /**
     * 递归获取父节点资源列表
     *
     * @param resource 资源
     * @return 资源列表
     */
    private List<Resource> getPreviousLevel(Resource resource) {
        List<Resource> tempList = new ArrayList<>();
        Resource temp_resource = resourceDao.findOne(resource.getParent());
        tempList.add(temp_resource);
        if ("0".equals(temp_resource.getParent())) {
            return tempList;
        } else {
            tempList.addAll(getPreviousLevel(temp_resource));
        }
        return tempList;
    }

    /**
     * 递归获取子节点资源列表
     *
     * @param list 资源列表
     * @return 资源列表
     */
    private List<Resource> getNextLevel(List<Resource> list) {
        List<Resource> tempList = new ArrayList<>();
        for (Resource resource : list) {
            tempList.add(resource);
            tempList.addAll(getNextLevel(resourceDao.findByParentOrderBySortNumAsc(resource.getId())));
        }
        return tempList;
    }

    /**
     * 有角色资源关系
     *
     * @param resourceID 资源ID
     * @return boolean
     */
    private boolean hasRoleResourceRelation(String resourceID) {
        List<RoleResourceRelation> roleResourceRelations = roleResourceRelationDao.findAllByResourceID(resourceID);
        if (null == roleResourceRelations || 0 == roleResourceRelations.size()) {
            // 如果未查询到
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否有子节点
     *
     * @param resourceID 资源ID
     * @return boolean
     */
    private boolean hasChildren(String resourceID) {
        List<Resource> resources = resourceDao.findByParentOrderBySortNumAsc(resourceID);
        if (null == resources || 0 == resources.size()) {
            //如果未查询到
            return false;
        } else {
            return true;
        }
    }
}
