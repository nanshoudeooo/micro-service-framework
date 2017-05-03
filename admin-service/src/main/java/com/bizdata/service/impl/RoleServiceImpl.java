package com.bizdata.service.impl;

import com.bizdata.dao.RoleDao;
import com.bizdata.dao.UserRoleRelationDao;
import com.bizdata.entity.Role;
import com.bizdata.entity.UserRoleRelation;
import com.bizdata.extend.BeanCopyUtil;
import com.bizdata.jpa.vo.JpaPageParamVO;
import com.bizdata.jpa.vo.JpaSortParamVO;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.RoleService;
import com.bizdata.vo.role.RoleCreateParamVO;
import com.bizdata.vo.role.RoleDeleteParamVO;
import com.bizdata.vo.role.RoleUpdateParamVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleRelationDao userRoleRelationDao;

    @Override
    public ResultStateVO create(RoleCreateParamVO roleCreateParamVO) {
        ResultStateVO resultStateVO;
        try {
            //判断角色名称是否存在
            Role tempRole = roleDao.findByRolename(roleCreateParamVO.getRolename());
            if (!checkRolenameNotExist(roleCreateParamVO.getRolename())) {
                resultStateVO = ResultStateUtil.create(1, "角色创建失败,请确认角色名称不重复!");
            } else {
                Role rolePO = new Role();
                BeanUtils.copyProperties(roleCreateParamVO, rolePO);
                roleDao.save(rolePO);
                resultStateVO = ResultStateUtil.create(0, "角色创建成功!");
            }
        } catch (Exception e) {
            logger.error("角色创建失败", e);
            resultStateVO = ResultStateUtil.create(2, "角色创建失败!");
        }
        return resultStateVO;
    }

    @Override
    public ResultStateVO update(RoleUpdateParamVO roleUpdateParamVO) {
        ResultStateVO resultStateVO;
        try {
            if (!checkRolenameNotExist(roleUpdateParamVO.getRolename())) {
                resultStateVO = ResultStateUtil.create(1, "角色更新失败,请确认角色名称不重复!");
            } else {
                Role rolePO = new Role();
                BeanCopyUtil.copyProperties(roleUpdateParamVO, rolePO);
                roleDao.save(rolePO);
                resultStateVO = ResultStateUtil.create(0, "角色更新成功!");
            }
        } catch (Exception e) {
            logger.error("角色更新失败", e);
            resultStateVO = ResultStateUtil.create(2, "角色更新失败");
        }
        return resultStateVO;
    }

    @Override
    public ResultStateVO delete(RoleDeleteParamVO roleDeleteParamVO) {
        ResultStateVO resultStateVO;
        try {
            if (checkBuiltInRole(roleDeleteParamVO.getId())) {
                //如果是系统内置角色
                resultStateVO = ResultStateUtil.create(2, "无法删除系统内置角色!");
            } else {
                roleDao.delete(roleDeleteParamVO.getId());
                resultStateVO = ResultStateUtil.create(0, "角色删除成功!");
            }
        } catch (Exception e) {
            logger.error("角色删除失败", e);
            resultStateVO = ResultStateUtil.create(3, "角色删除失败");
        }
        return resultStateVO;
    }

    @Override
    public Page<Role> findAll(JpaPageParamVO jpaPageParamVO, JpaSortParamVO jpaSortParamVO) {
        Page<Role> page = null;
        try {
            page = roleDao.findAll(jpaPageParamVO.getPageable(jpaSortParamVO.getSort()));
        } catch (Exception e) {
            logger.error("查询角色列表失败", e);
        }
        return page;
    }

    @Override
    public List<Role> findAllByUserID(String userID) {
        //获取用户角色关系列表
        List<UserRoleRelation> userRoleRelations = userRoleRelationDao.findByUserID(userID);
        List<Role> roles = new ArrayList<>();
        for (UserRoleRelation userRoleRelation : userRoleRelations) {
            roles.add(roleDao.findOne(userRoleRelation.getRoleID()));
        }
        return roles;
    }

    @Override
    public Role findOne(String roleID) {
        return roleDao.findOne(roleID);
    }

    /**
     * 判断角色名不存在
     *
     * @param rolename 角色名称
     * @return boolean
     */
    private boolean checkRolenameNotExist(String rolename) {
        Role tempRole = roleDao.findByRolename(rolename);
        if (null == tempRole) {
            //如果不存在
            return true;
        }
        return false;
    }

    /**
     * 根据角色ID判断是否为系统内置角色
     *
     * @param roleID 角色ID
     * @return boolean
     */
    private boolean checkBuiltInRole(String roleID) {
        Role role = roleDao.findOne(roleID);
        if (role.isBuiltIn()) {
            return true;
        }
        return false;
    }
}
