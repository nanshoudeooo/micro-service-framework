package com.bizdata.service.impl;

import com.bizdata.controller.role.vo.in.InDeleteVO;
import com.bizdata.controller.role.vo.in.InSaveVO;
import com.bizdata.controller.role.vo.in.InUpdateVO;
import com.bizdata.controller.user.vo.out.OutUserVO;
import com.bizdata.dao.RoleDao;
import com.bizdata.dao.UserDao;
import com.bizdata.dao.UserRoleRelationDao;
import com.bizdata.extend.BeanCopyUtil;
import com.bizdata.po.Role;
import com.bizdata.po.User;
import com.bizdata.po.UserRoleRelation;
import com.bizdata.service.RoleService;
import com.bizdata.service.UserOrganizationService;
import me.sdevil507.vo.JpaListPO2VO;
import me.sdevil507.vo.JpaPageParamVO;
import me.sdevil507.vo.JpaSortParamVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色Service实现
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RoleDao roleDao;

    private final UserDao userDao;

    private final UserRoleRelationDao userRoleRelationDao;

    private final UserOrganizationService userOrganizationService;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao, UserDao userDao, UserRoleRelationDao userRoleRelationDao, UserOrganizationService userOrganizationService) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.userRoleRelationDao = userRoleRelationDao;
        this.userOrganizationService = userOrganizationService;
    }

    @Override
    public boolean checkRoleNameExist(String roleName) {
        Role tempRole = roleDao.findByRolename(roleName);
        return null != tempRole;
    }

    @Override
    public boolean checkBuiltInRole(String roleID) {
        Role role = roleDao.findOne(roleID);
        return role.isBuiltIn();
    }

    @Override
    public boolean save(InSaveVO inSaveVO) {
        boolean state;
        try {
            Role role = new Role();
            BeanUtils.copyProperties(inSaveVO, role);
            roleDao.save(role);
            state = true;
        } catch (BeansException e) {
            logger.error("角色创建失败", e);
            state = false;
        }
        return state;
    }

    @Override
    public boolean update(InUpdateVO inUpdateVO) {
        boolean state;
        try {
            Role rolePO = new Role();
            BeanCopyUtil.copyProperties(inUpdateVO, rolePO);
            roleDao.save(rolePO);
            state = true;
        } catch (Exception e) {
            logger.error("角色更新失败", e);
            state = false;
        }
        return state;
    }

    @Override
    public boolean delete(InDeleteVO inDeleteVO) {
        boolean state;
        try {
            roleDao.delete(inDeleteVO.getId());
            state = true;
        } catch (Exception e) {
            logger.error("角色删除失败", e);
            state = false;
        }
        return state;
    }

    @Override
    public Page<Role> list(JpaPageParamVO jpaPageParamVO, JpaSortParamVO jpaSortParamVO) {
        Page<Role> page = null;
        try {
            page = roleDao.findAll(jpaPageParamVO.getPageable(jpaSortParamVO.getSort()));
        } catch (Exception e) {
            logger.error("查询角色列表失败", e);
        }
        return page;
    }

    @Override
    public List<Role> listByUserID(String userID) {
        //获取用户角色关系列表
        List<UserRoleRelation> userRoleRelations = userRoleRelationDao.findByUserID(userID);
        List<Role> roles = new ArrayList<>();
        for (UserRoleRelation userRoleRelation : userRoleRelations) {
            roles.add(roleDao.findOne(userRoleRelation.getRoleID()));
        }
        return roles;
    }

    @Override
    public Role getByID(String roleID) {
        return roleDao.findOne(roleID);
    }

    @Override
    public List<OutUserVO> listAuthorizedUsersByRoleIDAndOrganizationID(String roleID, String organizationID, String words) {
        List<OutUserVO> outUserVOs;
        try {
            //根据角色ID获取该角色下用户列表
            List<UserRoleRelation> userRoleRelations = userRoleRelationDao.findByRoleID(roleID);
            List<String> userRoleIds = new ArrayList<>();
            for (UserRoleRelation userRoleRelation : userRoleRelations) {
                userRoleIds.add(userRoleRelation.getUserID());
            }
            if (StringUtils.isEmpty(organizationID)) {
                //如果未传递组织机构ID条件,直接返回该角色下用户
                List<User> users = usernameOrRealNameLike(userDao.findByIdIn(userRoleIds), words);
                outUserVOs = JpaListPO2VO.convert(users, OutUserVO.class);
            } else {
                //如果包含组织机构ID条件
                //首先获取该组织机构下用户ID
                List<String> userOrganizationIds = userOrganizationService.findAllUserIDsRecursion(organizationID);

                //既属于角色又属于组织机构条件下的id列表
                List<String> intersectionIds = new ArrayList<>();
                for (String userOrganizationId : userOrganizationIds) {
                    if (userRoleIds.contains(userOrganizationId)) {
                        intersectionIds.add(userOrganizationId);
                    }
                }
                //根据交集ID列表返回用户列表
                List<User> users = usernameOrRealNameLike(userDao.findByIdIn(intersectionIds), words);
                outUserVOs = JpaListPO2VO.convert(users, OutUserVO.class);
            }
        } catch (Exception e) {
            outUserVOs = null;
            logger.error("获取授权用户列表失败!", e);
        }
        return outUserVOs;
    }

    /**
     * 模糊匹配筛选出符合要求的用户
     *
     * @param val 模糊查询条件
     * @return List<User>
     */
    private List<User> usernameOrRealNameLike(List<User> list, String val) {
        List<User> users = new ArrayList<>();
        if (StringUtils.isEmpty(val)) {
            users = list;
        } else {
            for (User user : list) {
                if (user.getUsername().contains(val) || user.getRealName().contains(val)) {
                    users.add(user);
                }
            }
        }
        return users;
    }

    @Override
    public List<OutUserVO> listUnauthorizedUsersByRoleIDAndOrganizationID(String roleID, String organizationID, String words) {
        List<OutUserVO> outUserVOs;
        try {
            //根据角色ID获取该角色下用户列表
            List<UserRoleRelation> userRoleRelations = userRoleRelationDao.findByRoleID(roleID);
            List<String> userRoleIds = new ArrayList<>();
            for (UserRoleRelation userRoleRelation : userRoleRelations) {
                userRoleIds.add(userRoleRelation.getUserID());
            }

            boolean state = true;
            if (0 == userRoleIds.size()) {
                // 该role下没有用户,则查询全部
                state = false;
            }

            if (StringUtils.isEmpty(organizationID)) {
                //如果未传递组织机构ID条件,直接返回该角色下未授权用户
                List<User> users;
                if (state) {
                    users = usernameOrRealNameLike(userDao.findByIdNotIn(userRoleIds), words);
                } else {
                    users = usernameOrRealNameLike(userDao.findAll(), words);
                }
                outUserVOs = JpaListPO2VO.convert(users, OutUserVO.class);
            } else {
                //如果包含组织机构ID条件
                //首先获取该组织机构下用户ID
                List<String> userOrganizationIds = userOrganizationService.findAllUserIDsRecursion(organizationID);

                //部门下的ID列表去除已授权的id
                List<String> intersectionIds = new ArrayList<>();

                for (String userOrganizationId : userOrganizationIds) {
                    if (!userRoleIds.contains(userOrganizationId)) {
                        intersectionIds.add(userOrganizationId);
                    }
                }

                //根据交集ID列表返回未授权用户列表
                List<User> users = usernameOrRealNameLike(userDao.findByIdIn(intersectionIds), words);
                outUserVOs = JpaListPO2VO.convert(users, OutUserVO.class);
            }
        } catch (Exception e) {
            outUserVOs = null;
            logger.error("获取未授权用户列表失败!", e);
        }
        return outUserVOs;
    }
}
