package com.bizdata.controller;

import com.bizdata.result.ResultStateVO;
import com.bizdata.service.OrganizationService;
import com.bizdata.vo.organization.CreateParamVO;
import com.bizdata.vo.organization.DeleteParamVO;
import com.bizdata.vo.organization.ReadByIDParamVO;
import com.bizdata.vo.organization.UpdateParamVO;
import com.bizdata.vo.organization.valid.create.ValidGroupCreate;
import com.bizdata.vo.organization.valid.delete.ValidGroupDelete;
import com.bizdata.vo.organization.valid.read.ValidGroupReadByID;
import com.bizdata.vo.organization.valid.update.ValidGroupUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * @param createParamVO 入参VO
     * @return ResultStateVO
     */
    @RequestMapping(value = "/organization/create", method = RequestMethod.POST)
    public ResultStateVO create(@Validated(ValidGroupCreate.class) CreateParamVO createParamVO) {
        return organizationService.create(createParamVO);
    }

    /**
     * 根据ID查询组织机构
     *
     * @param readByIDParamVO 入参VO
     * @return ResultStateVO
     */
    @RequestMapping(value = "/organization/readByID", method = RequestMethod.POST)
    public ResultStateVO readByID(@Validated(ValidGroupReadByID.class) ReadByIDParamVO readByIDParamVO) {
        return organizationService.readByID(readByIDParamVO);
    }

    /**
     * 更新组织机构
     *
     * @param updateParamVO 入参VO
     * @return ResultStateVO
     */
    @RequestMapping(value = "/organization/update", method = RequestMethod.POST)
    public ResultStateVO update(@Validated(ValidGroupUpdate.class) UpdateParamVO updateParamVO) {
        return organizationService.update(updateParamVO);
    }

    /**
     * 删除组织机构
     *
     * @param deleteParamVO 入参VO
     * @return ResultStateVO
     */
    @RequestMapping(value = "/organization/delete", method = RequestMethod.POST)
    public ResultStateVO delete(@Validated(ValidGroupDelete.class) DeleteParamVO deleteParamVO) {
        return organizationService.delete(deleteParamVO);
    }
}
