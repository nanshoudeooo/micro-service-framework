package com.bizdata.controller;

import com.bizdata.entity.User;
import com.bizdata.jpa.vo.JpaPageParamVO;
import com.bizdata.jpa.vo.JpaPageResultVO;
import com.bizdata.jpa.vo.JpaSortParamVO;
import com.bizdata.req.IdentityUtil;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.UserService;
import com.bizdata.vo.user.*;
import com.bizdata.vo.user.valid.create.ValidGroupUserCreate;
import com.bizdata.vo.user.valid.delete.ValidGroupUserDelete;
import com.bizdata.vo.user.valid.login.ValidGroupUserLogin;
import com.bizdata.vo.user.valid.read.ValidGroupUserReadOne;
import com.bizdata.vo.user.valid.update.ValidGroupUserUpdate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 执行登录操作
     *
     * @param userLoginParamVO 用户登录入参VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResultStateVO login(@Validated({ValidGroupUserLogin.class}) UserLoginParamVO userLoginParamVO) {
        ResultStateVO resultStateVO = userService.login(userLoginParamVO.getUsername(), userLoginParamVO.getPassword());
        //TODO 设置权限
        return resultStateVO;
    }

    /**
     * 执行退出操作
     *
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public ResultStateVO logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        return userService.logout(token);
    }

    /**
     * 用户新增
     *
     * @param userCreateParamVO 用户创建入参VO
     * @param request           用于获取header携带token
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResultStateVO create(@Validated(ValidGroupUserCreate.class) UserCreateParamVO userCreateParamVO, HttpServletRequest request) {
        String userID = IdentityUtil.getUserID(request);
        return userService.create(userCreateParamVO, userID);
    }

    /**
     * 根据用户id删除用户
     *
     * @param userDeleteParamVO 用户删除入参VO
     * @param request           用于获取header携带token
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public ResultStateVO delete(@Validated({ValidGroupUserDelete.class}) UserDeleteParamVO userDeleteParamVO, HttpServletRequest request) {
        String currentUserID = IdentityUtil.getUserID(request);
        return userService.delete(currentUserID, userDeleteParamVO.getId());
    }

    /**
     * 更新user
     *
     * @param userUpdateParamVO 用户更新入参VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ResultStateVO update(@Validated(ValidGroupUserUpdate.class) UserUpdateParamVO userUpdateParamVO) {
        return userService.update(userUpdateParamVO);
    }

    /**
     * 分页获取用户记录
     *
     * @param jpaPageParamVO 分页入参VO
     * @param jpaSortParamVO 排序入参VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/read", method = RequestMethod.POST)
    public ResultStateVO read(@Validated JpaPageParamVO jpaPageParamVO, @Validated JpaSortParamVO jpaSortParamVO) {
        ResultStateVO resultStateVO;
        Page<User> page = userService.findAll(jpaPageParamVO, jpaSortParamVO);
        if (null != page) {
            resultStateVO = ResultStateUtil.create(0, "查询用户成功!", new JpaPageResultVO(page, UserReadResultVO.class));
        } else {
            resultStateVO = ResultStateUtil.create(1, "查询用户失败!");
        }
        return resultStateVO;
    }

    /**
     * 根据用户id获取用户
     *
     * @param userReadOneParamVO 获取用户信息入参VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/readOne", method = RequestMethod.POST)
    public ResultStateVO readOne(@Validated({ValidGroupUserReadOne.class}) UserReadOneParamVO userReadOneParamVO) {
        ResultStateVO resultStateVO;
        User userPO = userService.findOne(userReadOneParamVO);
        if (null == userPO) {
            //如果不存在该用户
            resultStateVO = ResultStateUtil.create(1, "查询不到该用户");
        } else {
            UserReadResultVO userReadResultVO = new UserReadResultVO();
            BeanUtils.copyProperties(userPO, userReadResultVO);
            resultStateVO = ResultStateUtil.create(0, "查询用户成功", userReadResultVO);
        }
        return resultStateVO;
    }
}
