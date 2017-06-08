package com.bizdata.controller.user;

import com.bizdata.controller.user.vo.in.*;
import com.bizdata.controller.user.vo.in.valid.group.*;
import com.bizdata.controller.user.vo.out.OutIncludedOrganizationVO;
import com.bizdata.controller.user.vo.out.OutLoginVO;
import com.bizdata.controller.user.vo.out.OutUserVO;
import com.bizdata.po.User;
import com.bizdata.req.IdentityUtil;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.UserOrganizationService;
import com.bizdata.service.UserService;
import me.sdevil507.vo.JpaPageParamVO;
import me.sdevil507.vo.JpaPageResultVO;
import me.sdevil507.vo.JpaSortParamVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    private final UserOrganizationService userOrganizationService;

    @Autowired
    public UserController(UserService userService, UserOrganizationService userOrganizationService) {
        this.userService = userService;
        this.userOrganizationService = userOrganizationService;
    }

    /**
     * 执行登录操作
     *
     * @param inLoginVO 用户登录入参VO
     * @return 登录信息
     * @see OutLoginVO
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResultStateVO login(@Validated({ValidGroupLogin.class}) InLoginVO inLoginVO) {
        ResultStateVO resultStateVO;
        User user = userService.getByUsernameAndPassword(inLoginVO.getUsername(), inLoginVO.getPassword());
        if (null == user) {
            resultStateVO = ResultStateUtil.create(1, "登录失败,请确认用户名密码正确!");
        } else {
            if (!userService.checkUserAvailable(user)) {
                resultStateVO = ResultStateUtil.create(2, "登录失败,该账户被锁定,请联系管理员");
            } else {
                String token = userService.loginSuccess(user);
                resultStateVO = ResultStateUtil.create(0, "登录成功", new OutLoginVO(token));
            }
        }
        return resultStateVO;
    }

    /**
     * 执行退出操作
     *
     * @return 执行反馈
     */
    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public ResultStateVO logout(HttpServletRequest request) {
        ResultStateVO resultStateVO;
        String token = request.getHeader("token");
        if (userService.logout(token)) {
            resultStateVO = ResultStateUtil.create(0, "用户注销成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "用户注销失败!");
        }
        return resultStateVO;
    }

    /**
     * 用户新增
     *
     * @param inSaveVO 用户创建入参VO
     * @param request  用于获取header携带token
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public ResultStateVO save(@Validated(ValidGroupSave.class) InSaveVO inSaveVO, HttpServletRequest request) {
        ResultStateVO resultStateVO;
        String userID = IdentityUtil.getUserID(request);
        boolean state = userService.save(inSaveVO, userID);
        if (state) {
            resultStateVO = ResultStateUtil.create(0, "用户创建成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "用户创建失败!");
        }
        return resultStateVO;
    }

    /**
     * 根据用户id删除用户
     *
     * @param inDeleteVO 用户删除入参VO
     * @param request    用于获取header携带token
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public ResultStateVO delete(@Validated({ValidGroupDelete.class}) InDeleteVO inDeleteVO, HttpServletRequest request) {
        ResultStateVO resultStateVO;
        String currentUserID = IdentityUtil.getUserID(request);
        if (userService.checkUserIdSelf(currentUserID, inDeleteVO.getId())) {
            resultStateVO = ResultStateUtil.create(1, "当前操作用户无法删除自己!");
        } else {
            if (userService.checkBuiltInUser(inDeleteVO.getId())) {
                resultStateVO = ResultStateUtil.create(2, "无法删除系统内置用户!");
            } else {
                if (userService.delete(inDeleteVO.getId())) {
                    resultStateVO = ResultStateUtil.create(0, "用户删除成功!");
                } else {
                    resultStateVO = ResultStateUtil.create(3, "用户删除失败!");
                }
            }
        }
        return resultStateVO;
    }

    /**
     * 更新user
     *
     * @param inUpdateVO 用户更新入参VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ResultStateVO update(@Validated(ValidGroupUpdate.class) InUpdateVO inUpdateVO) {
        ResultStateVO resultStateVO;
        if (userService.update(inUpdateVO)) {
            resultStateVO = ResultStateUtil.create(0, "用户更新成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "用户更新失败!");
        }
        return resultStateVO;
    }

    /**
     * 分页获取用户记录
     *
     * @param jpaPageParamVO 分页入参VO
     * @param jpaSortParamVO 排序入参VO
     * @return 分页后用户信息列表
     * @see JpaPageResultVO<User, OutUserVO>
     */
    @RequestMapping(value = "/user/list", method = RequestMethod.POST)
    public ResultStateVO list(@Validated JpaPageParamVO jpaPageParamVO, @Validated JpaSortParamVO jpaSortParamVO, InSearchVO inSearchVO) {
        ResultStateVO resultStateVO;
        Page<User> page = userService.list(jpaPageParamVO, jpaSortParamVO, inSearchVO);
        JpaPageResultVO<User, OutUserVO> jpaPageResultVO = new JpaPageResultVO<>(page, OutUserVO.class);
        List<OutUserVO> outUserVOs = jpaPageResultVO.getRows();
        for (OutUserVO outUserVO : outUserVOs) {
            //根据ID查询出组织机构
            outUserVO.setOutIncludedOrganizationVOs(userOrganizationService.getOutIncludedOrganizationVOs(outUserVO.getId()));
        }
        if (null != page) {
            resultStateVO = ResultStateUtil.create(0, "查询用户成功!", jpaPageResultVO);
        } else {
            resultStateVO = ResultStateUtil.create(1, "查询用户失败!");
        }
        return resultStateVO;
    }

    /**
     * 获取登录用户信息
     *
     * @return 用户信息
     * @see OutUserVO
     */
    @RequestMapping(value = "/user/readSelf", method = RequestMethod.POST)
    public ResultStateVO readSelf(HttpServletRequest request) {
        ResultStateVO resultStateVO;
        try {
            User userPO = userService.getByID(IdentityUtil.getUserID(request));
            if (null == userPO) {
                //如果不存在该用户
                resultStateVO = ResultStateUtil.create(1, "查询不到该用户");
            } else {
                OutUserVO outUserVO = new OutUserVO();
                BeanUtils.copyProperties(userPO, outUserVO);
                //查询出该用户所属的组织机构
                List<OutIncludedOrganizationVO> outIncludedOrganizationVOs = userOrganizationService.getOutIncludedOrganizationVOs(userPO.getId());
                outUserVO.setOutIncludedOrganizationVOs(outIncludedOrganizationVOs);
                resultStateVO = ResultStateUtil.create(0, "读取登录用户信息成功!", outUserVO);
            }
        } catch (Exception e) {
            logger.error("读取登录用户信息失败!", e);
            resultStateVO = ResultStateUtil.create(2, "读取登录用户信息失败!");
        }
        return resultStateVO;
    }

    /**
     * 根据ID获取用户信息
     *
     * @param inGetByIdVO 入参VO
     * @return 用户信息
     * @see OutUserVO
     */
    @RequestMapping(value = "/user/getByID", method = RequestMethod.POST)
    public ResultStateVO getByID(@Validated(ValidGroupGetByID.class) InGetByIdVO inGetByIdVO) {
        ResultStateVO resultStateVO;
        try {
            User userPO = userService.getByID(inGetByIdVO.getId());
            if (null == userPO) {
                //如果不存在该用户
                resultStateVO = ResultStateUtil.create(1, "根据ID查询用户信息失败,不存在该用户!");
            } else {
                OutUserVO outUserVO = new OutUserVO();
                BeanUtils.copyProperties(userPO, outUserVO);
                //查询出该用户所属的组织机构
                List<OutIncludedOrganizationVO> outIncludedOrganizationVOs = userOrganizationService.getOutIncludedOrganizationVOs(userPO.getId());
                outUserVO.setOutIncludedOrganizationVOs(outIncludedOrganizationVOs);
                resultStateVO = ResultStateUtil.create(0, "根据ID查询用户信息成功!", outUserVO);
            }
        } catch (Exception e) {
            logger.error("根据ID获取用户信息失败!", e);
            resultStateVO = ResultStateUtil.create(2, "根据ID获取用户信息失败!");
        }
        return resultStateVO;
    }

    /**
     * 检测用户名是否重复
     *
     * @param username 用户名
     * @return boolean[true:重复,false:不重复]
     */
    @RequestMapping(value = "/user/validDuplicateUsername", method = RequestMethod.POST)
    public ResultStateVO validDuplicateUsername(String username) {
        ResultStateVO resultStateVO;
        //true代表重复 false不重复
        boolean isDuplicate;
        try {
            //检测数据库中是否已经存在
            isDuplicate = userService.validDuplicateUsername(username);
            resultStateVO = ResultStateUtil.create(0, "根据ID查询用户信息成功!", isDuplicate);
        } catch (Exception e) {
            logger.error("检测用户名是否已存在失败!", e);
            resultStateVO = ResultStateUtil.create(1, "检测用户名是否已存在失败!");
        }
        return resultStateVO;
    }

    /**
     * 重置用户密码
     *
     * @param inResetPasswordVO 重置密码入参
     * @return ResultStateVO执行反馈
     */
    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
    public ResultStateVO resetPassword(@Validated(ValidGroupResetPassword.class) InResetPasswordVO inResetPasswordVO) {
        ResultStateVO resultStateVO;
        if (userService.resetPassword(inResetPasswordVO.getId(), inResetPasswordVO.getPassword())) {
            resultStateVO = ResultStateUtil.create(0, "重置密码成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "重置密码失败");
        }
        return resultStateVO;
    }
}
