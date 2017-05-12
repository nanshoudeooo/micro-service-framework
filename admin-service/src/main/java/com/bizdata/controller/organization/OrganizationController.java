package com.bizdata.controller.organization;

import com.bizdata.controller.organization.vo.out.OutOrganizationVO;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import com.bizdata.service.OrganizationService;
import com.bizdata.controller.organization.vo.in.InSaveVO;
import com.bizdata.controller.organization.vo.in.InDeleteVO;
import com.bizdata.controller.organization.vo.in.InGetByIdVO;
import com.bizdata.controller.organization.vo.in.InUpdateVO;
import com.bizdata.controller.organization.vo.in.valid.group.ValidGroupSave;
import com.bizdata.controller.organization.vo.in.valid.group.ValidGroupDelete;
import com.bizdata.controller.organization.vo.in.valid.group.ValidGroupGetById;
import com.bizdata.controller.organization.vo.in.valid.group.ValidGroupUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 组织机构Controller
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    /**
     * 新增组织机构
     *
     * @param inSaveVO 入参VO
     * @return ResultStateVO
     */
    @RequestMapping(value = "/organization/save", method = RequestMethod.POST)
    public ResultStateVO save(@Validated(ValidGroupSave.class) InSaveVO inSaveVO) {
        ResultStateVO resultStateVO;
        if (organizationService.save(inSaveVO)) {
            resultStateVO = ResultStateUtil.create(0, "组织机构新增成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "组织机构新增失败!");
        }
        return resultStateVO;
    }

    /**
     * 根据ID查询组织机构
     *
     * @param inGetByIdVO 入参VO
     * @return ResultStateVO
     */
    @RequestMapping(value = "/organization/getByID", method = RequestMethod.POST)
    public ResultStateVO getByID(@Validated(ValidGroupGetById.class) InGetByIdVO inGetByIdVO) {
        ResultStateVO resultStateVO;
        OutOrganizationVO outOrganizationVO = organizationService.getByID(inGetByIdVO);
        if (null != outOrganizationVO) {
            resultStateVO = ResultStateUtil.create(0, "根据ID查询组织机构详情成功!", outOrganizationVO);
        } else {
            resultStateVO = ResultStateUtil.create(1, "根据ID查询组织机构详情失败!");
        }
        return resultStateVO;
    }

    /**
     * 更新组织机构
     *
     * @param inUpdateVO 入参VO
     * @return ResultStateVO
     */
    @RequestMapping(value = "/organization/update", method = RequestMethod.POST)
    public ResultStateVO update(@Validated(ValidGroupUpdate.class) InUpdateVO inUpdateVO) {
        ResultStateVO resultStateVO;
        boolean state = organizationService.update(inUpdateVO);
        if (state) {
            resultStateVO = ResultStateUtil.create(0, "组织机构更新成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "组织机构更新失败!");
        }
        return resultStateVO;
    }

    /**
     * 删除组织机构
     *
     * @param inDeleteVO 入参VO
     * @return ResultStateVO
     */
    @RequestMapping(value = "/organization/delete", method = RequestMethod.POST)
    public ResultStateVO delete(@Validated(ValidGroupDelete.class) InDeleteVO inDeleteVO) {
        ResultStateVO resultStateVO;
        boolean state = organizationService.delete(inDeleteVO);
        if (state) {
            resultStateVO = ResultStateUtil.create(0, "组织机构删除成功!");
        } else {
            resultStateVO = ResultStateUtil.create(1, "组织机构删除失败!");
        }
        return resultStateVO;
    }

    /**
     * 查询组织机构列表
     *
     * @return ResultStateVO
     * @see OutOrganizationVO
     */
    @RequestMapping(value = "/organization/list", method = RequestMethod.POST)
    public ResultStateVO list() {
        ResultStateVO resultStateVO;
        List<OutOrganizationVO> outOrganizationVOs = organizationService.list();
        if (null != outOrganizationVOs) {
            resultStateVO = ResultStateUtil.create(0, "查询组织机构列表成功!", outOrganizationVOs);
        } else {
            resultStateVO = ResultStateUtil.create(1, "查询组织机构列表失败!");
        }
        return resultStateVO;
    }
}
