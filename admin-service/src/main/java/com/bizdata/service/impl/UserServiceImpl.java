package com.bizdata.service.impl;

import com.bizdata.controller.user.vo.in.InSaveVO;
import com.bizdata.controller.user.vo.in.InSearchVO;
import com.bizdata.controller.user.vo.in.InUpdateVO;
import com.bizdata.dao.UserDao;
import com.bizdata.dao.UserOrganizationDao;
import com.bizdata.extend.BeanCopyUtil;
import com.bizdata.po.User;
import com.bizdata.po.UserOrganizationRelation;
import com.bizdata.service.UserRoleRelationService;
import com.bizdata.service.UserService;
import com.bizdata.token.TokenServiceFeign;
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

    private final UserDao userDao;

    private final UserRoleRelationService userRoleRelationService;

    private final UserOrganizationDao userOrganizationDao;

    private final TokenServiceFeign tokenServiceFeign;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserRoleRelationService userRoleRelationService, UserOrganizationDao userOrganizationDao, TokenServiceFeign tokenServiceFeign) {
        this.userDao = userDao;
        this.userRoleRelationService = userRoleRelationService;
        this.userOrganizationDao = userOrganizationDao;
        this.tokenServiceFeign = tokenServiceFeign;
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            user = userDao.findByUsernameAndPassword(username, md5Password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean checkUserAvailable(User user) {
        System.out.println(user.isAvailable());
        return user.isAvailable();
    }

    @Override
    public String loginSuccess(User user) {
        String token = "";
        try {
            //更新最后登录时间
            user.setLastLoginTime(new Date());
            userDao.save(user);
            //如果正确,使用该user的userid申请token
            token = tokenServiceFeign.createToken("admin", user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(user.getUsername() + "登录失败", e);
        }
        return token;
    }


    @Override
    public boolean logout(String token) {
        boolean state;
        try {
            tokenServiceFeign.removeToken(token);
            state = true;
        } catch (Exception ex) {
            state = false;
            logger.error("删除token:" + token + "失败", ex);
        }
        return state;
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

    @Override
    public boolean checkUserIdSelf(String currentUserID, String targetUserID) {
        return currentUserID.equals(targetUserID);
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
    public boolean delete(String userID) {
        boolean state;
        try {
            userDao.delete(userID);
            //同时删除用户角色关系
            userRoleRelationService.deleteByUserID(userID);
            state = true;
        } catch (Exception e) {
            logger.error("用户删除失败", e);
            state = false;
        }
        return state;
    }

    @CacheEvict(value = "adminUserList", allEntries = true)
    @Override
    @Transactional
    public boolean update(InUpdateVO inUpdateVO) {
        boolean state;
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
            state = true;
        } catch (Exception e) {
            logger.error("更新用户失败", e);
            state = false;
        }
        return state;
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
        return userDao.findOne(userID);
    }

    @Override
    public boolean validDuplicateUsername(String username) {
        return !checkUsernameNotExist(username);
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

    public boolean checkBuiltInUser(String userID) {
        User user = userDao.findOne(userID);
        return user.isBuiltIn();
    }

    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @return boolean执行反馈
     */
    private boolean checkUsernameNotExist(String username) {
        User user = userDao.findByUsername(username);
        return null == user;
    }
}
