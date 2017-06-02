package com.bizdata.service.impl;

import com.bizdata.controller.user.vo.in.InSearchVO;
import com.bizdata.dao.UserDao;
import com.bizdata.dao.UserOrganizationDao;
import com.bizdata.po.User;
import com.bizdata.extend.BeanCopyUtil;
import com.bizdata.po.UserOrganizationRelation;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.UserRoleRelationService;
import com.bizdata.service.UserService;
import com.bizdata.token.TokenServiceFeign;
import com.bizdata.controller.user.vo.in.InSaveVO;
import com.bizdata.controller.user.vo.out.OutLoginVO;
import com.bizdata.controller.user.vo.in.InUpdateVO;
import me.sdevil507.vo.JpaPageParamVO;
import me.sdevil507.vo.JpaSortParamVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.Date;

/**
 * 用户操作相关接口实现
 * <p>
 * Created by sdevil507 on 2017/4/7.
 */

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleRelationService userRoleRelationService;

    @Autowired
    private UserOrganizationDao userOrganizationDao;

    @Autowired
    private TokenServiceFeign tokenServiceFeign;

    @Override
    @Transactional
    public ResultStateVO login(String username, String password) {
        ResultStateVO resultStateVO;
        try {
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            User user = userDao.findByUsernameAndPassword(username, md5Password);
            if (null != user) {
                //账户验证成功
                if (user.isAvailable()) {
                    //账户可用
                    //更新最后登录时间
                    user.setLastLoginTime(new Date());
                    userDao.save(user);
                    //如果正确,使用该user的userid申请token
                    String token = tokenServiceFeign.createToken("admin", user.getId());
                    resultStateVO = ResultStateUtil.create(0, "登录成功", new OutLoginVO(token));
                } else {
                    //账户不可用
                    resultStateVO = ResultStateUtil.create(2, "登录失败,该账户被锁定,请联系管理员");
                }
            } else {
                resultStateVO = ResultStateUtil.create(1, "登录失败,请确认用户名密码正确!");
            }
        } catch (Exception ex) {
            logger.error(username + "登录失败", ex);
            resultStateVO = ResultStateUtil.create(3, "登录失败");
        }
        return resultStateVO;
    }


    @Override
    public ResultStateVO logout(String token) {
        ResultStateVO resultStateVO;
        try {
            tokenServiceFeign.removeToken(token);
            resultStateVO = ResultStateUtil.create(0, "用户注销成功!");
        } catch (Exception ex) {
            resultStateVO = ResultStateUtil.create(1, "用户注销失败!");
            logger.error("删除token:" + token + "失败", ex);
        }
        return resultStateVO;
    }

    @CacheEvict(value = {"adminUserList"}, allEntries = true)
    @Override
    @Transactional
    public boolean save(InSaveVO inSaveVO, String userID) {
        boolean state;
        try {
            //VO转PO
            User userPO = new User();
            BeanUtils.copyProperties(inSaveVO, userPO);
            userPO.setCreateTime(new Date());
            userPO.setLastLoginTime(new Date());
            userPO.setLastUpdateTime(new Date());
            userPO.setCreator(userID);
            userPO.setAvailable(true);
            //设置用户密码md5加密
            String md5Pwd = DigestUtils.md5DigestAsHex(userPO.getPassword().getBytes());
            userPO.setPassword(md5Pwd);
            User resultUser = userDao.save(userPO);
            //设置用户组织机构关系
            String[] organizationIDs = inSaveVO.getOrganizationIDs();
            saveRelationByOrganizationIDs(organizationIDs, resultUser.getId());
            state = true;
        } catch (Exception ex) {
            logger.error("创建用户失败", ex);
            state = false;
        }
        return state;
    }

    /**
     * 根据组织机构ID列表循环插入关系
     *
     * @param organizationIDs 组织机构ID列表
     * @param id              用户ID
     */
    private void saveRelationByOrganizationIDs(String[] organizationIDs, String id) {
        for (String organizationID : organizationIDs) {
            UserOrganizationRelation userOrganizationRelation = new UserOrganizationRelation();
            userOrganizationRelation.setUserID(id);
            userOrganizationRelation.setOrganizationID(organizationID);
            userOrganizationDao.save(userOrganizationRelation);
        }
    }

    @CacheEvict(value = "adminUserList", allEntries = true)
    @Override
    @Transactional
    public ResultStateVO delete(String currentUserID, String userID) {
        ResultStateVO resultStateVO;
        try {
            if (currentUserID.equals(userID)) {
                //如果待删除用户为当前操作用户
                resultStateVO = ResultStateUtil.create(1, "当前操作用户无法删除自身!");
            } else {
                if (checkBuiltInUser(userID)) {
                    //如果删除的是系统内置用户,提示无法删除
                    resultStateVO = ResultStateUtil.create(2, "无法删除系统内置用户!");
                } else {
                    userDao.delete(userID);
                    //同时删除用户角色关系
                    userRoleRelationService.deleteByUserID(userID);
                    resultStateVO = ResultStateUtil.create(0, "删除用户成功!");
                }
            }
        } catch (Exception ex) {
            logger.error("用户删除失败", ex);
            resultStateVO = ResultStateUtil.create(3, "删除用户失败");
        }
        return resultStateVO;
    }

    @CacheEvict(value = "adminUserList", allEntries = true)
    @Override
    @Transactional
    public ResultStateVO update(InUpdateVO inUpdateVO) {
        ResultStateVO resultStateVO;
        try {
            //查询出原用户
            User userPO = userDao.findOne(inUpdateVO.getId());
            BeanCopyUtil.copyProperties(inUpdateVO, userPO);
            userPO.setLastUpdateTime(new Date());
            userDao.save(userPO);
            //根据用户ID与组织机构ID更新
            userOrganizationDao.deleteByUserID(inUpdateVO.getId());
            String[] organizationIDs = inUpdateVO.getOrganizationIDs();
            saveRelationByOrganizationIDs(organizationIDs, inUpdateVO.getId());
            resultStateVO = ResultStateUtil.create(0, "用户更新成功!");
        } catch (Exception e) {
            logger.error("更新用户失败", e);
            resultStateVO = ResultStateUtil.create(2, "用户更新失败!");
        }
        return resultStateVO;
    }

    @Cacheable(value = "adminUserList")
    @Override
    public Page<User> list(JpaPageParamVO jpaPageParamVO, JpaSortParamVO jpaSortParamVO, InSearchVO inSearchVO) {
        Page<User> page = null;
        Specification specification = null;
        try {
            if (!StringUtils.isEmpty(inSearchVO.getUsername())) {
                //如果查询条件用户名不为空
                specification = new Specification() {
                    @Override
                    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                        Path<String> username = root.get("username");
                        query.where(cb.like(username, "%" + inSearchVO.getUsername() + "%"));
                        return null;
                    }
                };
            }
            page = userDao.findAll(specification, jpaPageParamVO.getPageable(jpaSortParamVO.getSort()));
        } catch (Exception e) {
            logger.error("分页查询用户失败", e);
        }
        return page;
    }

    @Override
    public User getByID(String userID) {
        User user = userDao.findOne(userID);
        return user;
    }

    @Override
    public boolean validDuplicateUsername(String username) {
        if (checkUsernameNotExist(username)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean resetPassword(String userID, String password) {
        boolean state;
        try {
            User user = userDao.findOne(userID);
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            user.setPassword(password);
            userDao.save(user);
            state = true;
        } catch (Exception e) {
            logger.error("重置密码失败:", e);
            state = false;
        }
        return state;
    }

    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @return boolean执行反馈
     */
    private boolean checkUsernameNotExist(String username) {
        User user = userDao.findByUsername(username);
        if (null == user) {
            return true;
        }
        return false;
    }

    /**
     * 根据userID判断用户是否为系统内置用户
     *
     * @param userID 用户ID
     * @return boolean
     */
    private boolean checkBuiltInUser(String userID) {
        User user = userDao.findOne(userID);
        if (user.isBuiltIn()) {
            //如果为系统内置用户
            return true;
        }
        return false;
    }
}
