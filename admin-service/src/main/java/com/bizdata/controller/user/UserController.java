package com.bizdata.controller.user;

import com.bizdata.controller.user.vo.in.*;
import com.bizdata.controller.user.vo.in.valid.group.*;
import com.bizdata.controller.user.vo.out.OutIncludedOrganizationVO;
import com.bizdata.controller.user.vo.out.OutUserVO;
import com.bizdata.dao.OrganizationDao;
import com.bizdata.dao.UserOrganizationDao;
import com.bizdata.jpa.vo.JpaPageParamVO;
import com.bizdata.jpa.vo.JpaPageResultVO;
import com.bizdata.jpa.vo.JpaSortParamVO;
import com.bizdata.po.Organization;
import com.bizdata.po.User;
import com.bizdata.po.UserOrganizationRelation;
import com.bizdata.req.IdentityUtil;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.UserOrganizationService;
import com.bizdata.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrganizationDao userOrganizationDao;

    @Autowired
    private OrganizationDao organizationDao;

    /**
     * 执行登录操作
     *
     * @param inLoginVO 用户登录入参VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResultStateVO login(@Validated({ValidGroupLogin.class}) InLoginVO inLoginVO) {
        return userService.login(inLoginVO.getUsername(), inLoginVO.getPassword());
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
        String currentUserID = IdentityUtil.getUserID(request);
        return userService.delete(currentUserID, inDeleteVO.getId());
    }

    /**
     * 更新user
     *
     * @param inUpdateVO 用户更新入参VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ResultStateVO update(@Validated(ValidGroupUpdate.class) InUpdateVO inUpdateVO) {
        return userService.update(inUpdateVO);
    }

    /**
     * 分页获取用户记录
     *
     * @param jpaPageParamVO 分页入参VO
     * @param jpaSortParamVO 排序入参VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/user/list", method = RequestMethod.POST)
    public ResultStateVO list(@Validated JpaPageParamVO jpaPageParamVO, @Validated JpaSortParamVO jpaSortParamVO, InSearchVO inSearchVO) {
        ResultStateVO resultStateVO;
        Page<User> page = userService.list(jpaPageParamVO, jpaSortParamVO, inSearchVO);
        JpaPageResultVO jpaPageResultVO = new JpaPageResultVO(page, OutUserVO.class);
        List<OutUserVO> outUserVOs = jpaPageResultVO.getRows();
        for (OutUserVO outUserVO : outUserVOs) {
            //根据ID查询出组织机构
            outUserVO.setOutIncludedOrganizationVOs(getOutIncludedOrganizationVOs(outUserVO.getId()));
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
     * @return ResultStateVO类型执行反馈
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
                OutUserVO userReadResultVO = new OutUserVO();
                BeanUtils.copyProperties(userPO, userReadResultVO);
                //查询出该用户所属的组织机构
                List<OutIncludedOrganizationVO> outIncludedOrganizationVOs = getOutIncludedOrganizationVOs(userPO.getId());
                userReadResultVO.setOutIncludedOrganizationVOs(outIncludedOrganizationVOs);
                resultStateVO = ResultStateUtil.create(0, "读取登录用户信息成功!", userReadResultVO);
            }
        } catch (Exception e) {
            logger.error("读取登录用户信息失败!", e);
            resultStateVO = ResultStateUtil.create(2, "读取登录用户信息失败!");
        }
        return resultStateVO;
    }

    /**
     * 根据用户ID获取该用户组织机构列表
     *
     * @param id 用户
     * @return List<OutIncludedOrganizationVO>
     */
    private List<OutIncludedOrganizationVO> getOutIncludedOrganizationVOs(String id) {
        List<UserOrganizationRelation> userOrganizationRelations = userOrganizationDao.findByUserID(id);
        List<OutIncludedOrganizationVO> outIncludedOrganizationVOs = new ArrayList<>();
        for (UserOrganizationRelation userOrganizationRelation : userOrganizationRelations) {
            OutIncludedOrganizationVO outIncludedOrganizationVO = new OutIncludedOrganizationVO();
            Organization organization = organizationDao.findOne(userOrganizationRelation.getOrganizationID());
            outIncludedOrganizationVO.setId(organization.getId());
            outIncludedOrganizationVO.setName(organization.getName());
            outIncludedOrganizationVOs.add(outIncludedOrganizationVO);
        }
        return outIncludedOrganizationVOs;
    }

    /**
     * 根据ID获取用户信息
     *
     * @param inGetByIdVO 入参VO
     * @return ResultStateVO类型执行反馈
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
                OutUserVO userReadResultVO = new OutUserVO();
                BeanUtils.copyProperties(userPO, userReadResultVO);
                //查询出该用户所属的组织机构
                List<OutIncludedOrganizationVO> outIncludedOrganizationVOs = getOutIncludedOrganizationVOs(userPO.getId());
                userReadResultVO.setOutIncludedOrganizationVOs(outIncludedOrganizationVOs);
                resultStateVO = ResultStateUtil.create(0, "根据ID查询用户信息成功!", userReadResultVO);
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
     * @return ResultStateVO类型执行反馈
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

    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
    public ResultStateVO resetPassword(String userID, String password) {
        ResultStateVO resultStateVO;
        if (userService.resetPassword(userID, password)) {
            resultStateVO = ResultStateUtil.create(0, "重置密码成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "重置密码失败");
        }
        return resultStateVO;
    }
}
