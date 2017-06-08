package com.bizdata.controller.role;

import com.bizdata.controller.role.vo.in.InGetByIDVO;
import com.bizdata.controller.role.vo.in.valid.group.ValidGroupGetByID;
import com.bizdata.controller.user.vo.out.OutUserVO;
import com.bizdata.po.Role;
import com.bizdata.req.IdentityUtil;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.RoleService;
import com.bizdata.controller.role.vo.in.InSaveVO;
import com.bizdata.controller.role.vo.in.InDeleteVO;
import com.bizdata.controller.role.vo.out.OutRoleVO;
import com.bizdata.controller.role.vo.in.InUpdateVO;
import com.bizdata.controller.role.vo.in.valid.group.ValidGroupSave;
import com.bizdata.controller.role.vo.in.valid.group.ValidGroupDelete;
import com.bizdata.controller.role.vo.in.valid.group.ValidGroupUpdate;
import me.sdevil507.vo.JpaPageParamVO;
import me.sdevil507.vo.JpaPageResultVO;
import me.sdevil507.vo.JpaSortParamVO;
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

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 角色新增
     *
     * @param inSaveVO 角色创建入参VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/role/save", method = RequestMethod.POST)
    public ResultStateVO save(@Validated({ValidGroupSave.class}) InSaveVO inSaveVO) {
        ResultStateVO resultStateVO;
        try {
            if (roleService.checkRoleNameExist(inSaveVO.getRolename())) {
                resultStateVO = ResultStateUtil.create(1, "该角色名重复,不可创建!");
            } else {
                if (roleService.save(inSaveVO)) {
                    resultStateVO = ResultStateUtil.create(0, "角色创建成功!");
                } else {
                    resultStateVO = ResultStateUtil.create(2, "角色创建失败");
                }
            }
        } catch (Exception e) {
            logger.error("角色创建失败!", e);
            resultStateVO = ResultStateUtil.create(2, "角色创建失败");
        }
        return resultStateVO;
    }

    /**
     * 角色更新
     *
     * @param inUpdateVO 角色更新VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    public ResultStateVO update(@Validated({ValidGroupUpdate.class}) InUpdateVO inUpdateVO) {
        ResultStateVO resultStateVO;
        if (roleService.update(inUpdateVO)) {
            resultStateVO = ResultStateUtil.create(0, "角色更新成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "角色更新失败!");
        }
        return resultStateVO;
    }

    /**
     * 角色删除
     *
     * @param inDeleteVO 角色删除VO
     * @return ResultStateVO类型执行反馈
     */
    @RequestMapping(value = "/role/delete", method = RequestMethod.POST)
    public ResultStateVO delete(@Validated({ValidGroupDelete.class}) InDeleteVO inDeleteVO) {
        ResultStateVO resultStateVO;
        try {
            if (roleService.checkBuiltInRole(inDeleteVO.getId())) {
                resultStateVO = ResultStateUtil.create(1, "该角色为系统内置,无法删除!");
            } else {
                if (roleService.delete(inDeleteVO)) {
                    resultStateVO = ResultStateUtil.create(0, "角色删除成功!");
                } else {
                    resultStateVO = ResultStateUtil.create(2, "角色删除失败!");
                }
            }
        } catch (Exception e) {
            logger.error("角色删除失败!", e);
            resultStateVO = ResultStateUtil.create(2, "角色删除失败!");
        }
        return resultStateVO;
    }

    /**
     * 分页查询角色列表
     *
     * @param jpaPageParamVO 分页入参VO
     * @param jpaSortParamVO 排序入参VO
     * @return ResultStateVO类型执行反馈
     * @see JpaPageResultVO<Role,OutRoleVO>
     */
    @RequestMapping(value = "/role/list", method = RequestMethod.POST)
    public ResultStateVO list(@Validated JpaPageParamVO jpaPageParamVO, @Validated JpaSortParamVO jpaSortParamVO) {
        ResultStateVO resultStateVO;
        Page<Role> page = roleService.list(jpaPageParamVO, jpaSortParamVO);
        if (null != page) {
            resultStateVO = ResultStateUtil.create(0, "查询角色列表成功!", new JpaPageResultVO<>(page, OutRoleVO.class));
        } else {
            resultStateVO = ResultStateUtil.create(1, "查询角色列表失败!");
        }
        return resultStateVO;
    }

    /**
     * 获取当前登录用户角色信息
     *
     * @param request 请求
     * @return 角色信息列表
     * @see List<Role>
     */
    @RequestMapping(value = "/role/readSelf", method = RequestMethod.POST)
    public ResultStateVO readSelf(HttpServletRequest request) {
        ResultStateVO resultStateVO;
        String userID = IdentityUtil.getUserID(request);
        try {
            List<Role> roles = roleService.listByUserID(userID);
            resultStateVO = ResultStateUtil.create(0, "获取当前登录用户角色信息成功!", roles);
        } catch (Exception e) {
            logger.error("获取当前登录用户角色信息失败!", e);
            resultStateVO = ResultStateUtil.create(1, "获取当前登录用户角色信息失败!");
        }
        return resultStateVO;
    }

    /**
     * 根据角色id获取角色信息
     *
     * @param inGetByIDVO 入参
     * @return ResultStateVO执行反馈
     * @see Role
     */
    @RequestMapping(value = "/role/getByID", method = RequestMethod.POST)
    public ResultStateVO getByID(@Validated(ValidGroupGetByID.class) InGetByIDVO inGetByIDVO) {
        ResultStateVO resultStateVO;
        try {
            Role role = roleService.getByID(inGetByIDVO.getId());
            resultStateVO = ResultStateUtil.create(0, "获取角色信息成功!", role);
        } catch (Exception e) {
            logger.error("获取当前登录用户角色信息失败!", e);
            resultStateVO = ResultStateUtil.create(1, "获取角色信息失败!");
        }
        return resultStateVO;
    }

    /**
     * 获取已授权的用户列表
     *
     * @param roleID         角色ID
     * @param organizationID 组织机构ID
     * @param words          模糊匹配单词
     * @return ResultStateVO
     */
    @RequestMapping(value = "/role/listAuthorizedUsers", method = RequestMethod.POST)
    public ResultStateVO listAuthorizedUsers(String roleID, String organizationID, String words) {
        ResultStateVO resultStateVO;
        List<OutUserVO> outUserVOs = roleService.listAuthorizedUsersByRoleIDAndOrganizationID(roleID, organizationID, words);
        if (null != outUserVOs) {
            resultStateVO = ResultStateUtil.create(0, "获取已授权用户列表成功!", outUserVOs);
        } else {
            resultStateVO = ResultStateUtil.create(1, "获取已授权用户列表失败!");
        }
        return resultStateVO;
    }

    /**
     * 获取未授权的用户列表
     *
     * @param roleID         角色ID
     * @param organizationID 组织机构ID
     * @param words          模糊匹配单词
     * @return ResultStateVO
     */
    @RequestMapping(value = "/role/listUnauthorizedUsers", method = RequestMethod.POST)
    public ResultStateVO listUnauthorizedUsers(String roleID, String organizationID, String words) {
        ResultStateVO resultStateVO;
        List<OutUserVO> outUserVOs = roleService.listUnauthorizedUsersByRoleIDAndOrganizationID(roleID, organizationID, words);
        if (null != outUserVOs) {
            resultStateVO = ResultStateUtil.create(0, "获取已授权用户列表成功!", outUserVOs);
        } else {
            resultStateVO = ResultStateUtil.create(1, "获取已授权用户列表失败!");
        }
        return resultStateVO;
    }
}
