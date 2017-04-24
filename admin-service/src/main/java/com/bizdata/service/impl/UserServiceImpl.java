package com.bizdata.service.impl;

import com.bizdata.dao.UserDao;
import com.bizdata.entity.User;
import com.bizdata.extend.BeanCopyUtil;
import com.bizdata.jpa.vo.JpaPageParamVO;
import com.bizdata.jpa.vo.JpaSortParamVO;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.UserRoleRelationService;
import com.bizdata.service.UserService;
import com.bizdata.token.TokenServiceFeign;
import com.bizdata.vo.user.UserCreateParamVO;
import com.bizdata.vo.user.UserLoginResultVO;
import com.bizdata.vo.user.UserReadSearchParamVO;
import com.bizdata.vo.user.UserUpdateParamVO;
import org.slf4j.Logger;
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

    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleRelationService userRoleRelationService;

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
                    String token = tokenServiceFeign.createToken(user.getId());
                    resultStateVO = ResultStateUtil.create(0, "登录成功", new UserLoginResultVO(token));
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
    public ResultStateVO create(UserCreateParamVO userCreateParamVO, String userID) {
        ResultStateVO resultStateVO;
        //VO转PO
        User userPO = new User();
        BeanUtils.copyProperties(userCreateParamVO, userPO);
        userPO.setCreateTime(new Date());
        userPO.setLastLoginTime(new Date());
        userPO.setLastUpdateTime(new Date());
        userPO.setCreator(userID);
        userPO.setAvailable(true);
        try {
            //首先判断该用户是否存在
            if (checkUsernameNotExist(userPO.getUsername())) {
                //如果不存在
                //设置用户密码md5加密
                String md5Pwd = DigestUtils.md5DigestAsHex(userPO.getPassword().getBytes());
                userPO.setPassword(md5Pwd);
                userDao.save(userPO);
                resultStateVO = ResultStateUtil.create(0, "用户创建成功!");
            } else {
                resultStateVO = ResultStateUtil.create(1, "该用户名已经存在,请确保用户名未被占用!");
            }
        } catch (Exception ex) {
            logger.error("创建用户失败", ex);
            resultStateVO = ResultStateUtil.create(2, "用户创建失败");
        }
        return resultStateVO;
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
    public ResultStateVO update(UserUpdateParamVO userUpdateParamVO) {
        ResultStateVO resultStateVO;
        try {
            //查询用户名是否存在
            if (!checkUsernameNotExist(userUpdateParamVO.getUsername())) {
                //如果存在
                resultStateVO = ResultStateUtil.create(1, "用户名不可以重复");
            } else {
                //设置密码md5
                userUpdateParamVO.setPassword(DigestUtils.md5DigestAsHex(userUpdateParamVO.getPassword().getBytes()));
                //查询出原用户
                User userPO = userDao.findOne(userUpdateParamVO.getId());
                BeanCopyUtil.copyProperties(userUpdateParamVO, userPO);
                userPO.setLastUpdateTime(new Date());
                userDao.save(userPO);
                resultStateVO = ResultStateUtil.create(0, "用户更新成功!");
            }
        } catch (Exception e) {
            logger.error("更新用户失败", e);
            resultStateVO = ResultStateUtil.create(2, "用户更新失败!");
        }
        return resultStateVO;
    }

    @Cacheable(value = "adminUserList")
    @Override
    public Page<User> findAll(JpaPageParamVO jpaPageParamVO, JpaSortParamVO jpaSortParamVO, UserReadSearchParamVO userReadSearchParamVO) {
        Page<User> page = null;
        Specification specification = null;
        try {
            if (!StringUtils.isEmpty(userReadSearchParamVO.getUsername())) {
                //如果查询条件用户名不为空
                specification = new Specification() {
                    @Override
                    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                        Path<String> username = root.get("username");
                        query.where(cb.like(username, "%"+userReadSearchParamVO.getUsername()+"%"));
                        return null;
                    }
                };
            }
            page = userDao.findAll(specification, jpaPageParamVO.getPageable(jpaSortParamVO.getSort()));
            //TODO 需要包含角色信息
        } catch (Exception e) {
            logger.error("分页查询用户失败", e);
        }
        return page;
    }

    @Override
    public User findOne(String userID) {
        User user = userDao.findOne(userID);
        return user;
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
