package com.bizdata.controller;

import com.bizdata.entity.Role;
import com.bizdata.jpa.vo.JpaPageParamVO;
import com.bizdata.jpa.vo.JpaPageResultVO;
import com.bizdata.jpa.vo.JpaSortParamVO;
import com.bizdata.req.IdentityUtil;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.RoleService;
import com.bizdata.vo.role.RoleCreateParamVO;
import com.bizdata.vo.role.RoleDeleteParamVO;
import com.bizdata.vo.role.RoleReadResultVO;
import com.bizdata.vo.role.RoleUpdateParamVO;
import com.bizdata.vo.role.valid.create.ValidGroupRoleCreate;
import com.bizdata.vo.role.valid.delete.ValidGroupRoleDelete;
import com.bizdata.vo.role.valid.update.ValidGroupRoleUpdate;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色Controller
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
@RestController
public class RoleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleService roleService;

    /**
     * 角色新增
     *
     * @param roleCreateParamVO 角色创建入参VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/role/create", method = RequestMethod.POST)
    public ResultStateVO create(@Validated({ValidGroupRoleCreate.class}) RoleCreateParamVO roleCreateParamVO) {
        return roleService.create(roleCreateParamVO);
    }

    /**
     * 角色更新
     *
     * @param roleUpdateParamVO 角色更新VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    public ResultStateVO update(@Validated({ValidGroupRoleUpdate.class}) RoleUpdateParamVO roleUpdateParamVO) {
        return roleService.update(roleUpdateParamVO);
    }

    /**
     * 角色删除
     *
     * @param roleDeleteParamVO 角色删除VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/role/delete", method = RequestMethod.POST)
    public ResultStateVO delete(@Validated({ValidGroupRoleDelete.class}) RoleDeleteParamVO roleDeleteParamVO) {
        return roleService.delete(roleDeleteParamVO);
    }

    /**
     * 分页查询角色列表
     *
     * @param jpaPageParamVO 分页入参VO
     * @param jpaSortParamVO 排序入参VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/role/read", method = RequestMethod.POST)
    public ResultStateVO read(JpaPageParamVO jpaPageParamVO, JpaSortParamVO jpaSortParamVO) {
        ResultStateVO resultStateVO;
        Page<Role> page = roleService.findAll(jpaPageParamVO, jpaSortParamVO);
        if (null != page) {
            resultStateVO = ResultStateUtil.create(0, "查询角色列表成功!", new JpaPageResultVO(page, RoleReadResultVO.class));
        } else {
            resultStateVO = ResultStateUtil.create(1, "查询角色列表失败!");
        }
        return resultStateVO;
    }

    /**
     * 获取当前登录用户角色信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/role/readSelf",method = RequestMethod.POST)
    public ResultStateVO readSelf(HttpServletRequest request) {
        ResultStateVO resultStateVO;
        String userID = IdentityUtil.getUserID(request);
        try {
            List<Role> roles = roleService.findAllByUserID(userID);
            resultStateVO = ResultStateUtil.create(0, "获取当前登录用户角色信息成功!", roles);
        } catch (Exception e) {
            logger.error("获取当前登录用户角色信息失败!", e);
            resultStateVO = ResultStateUtil.create(1, "获取当前登录用户角色信息失败!");
        }
        return resultStateVO;
    }
}
