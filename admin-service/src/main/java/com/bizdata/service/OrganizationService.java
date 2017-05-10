package com.bizdata.service;

import com.bizdata.result.ResultStateVO;
import com.bizdata.vo.organization.CreateParamVO;
import com.bizdata.vo.organization.DeleteParamVO;
import com.bizdata.vo.organization.ReadByIDParamVO;
import com.bizdata.vo.organization.UpdateParamVO;

/**
 * 组织机构Service
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
public interface OrganizationService {

    /**
     * 组织机构新增
     *
     * @param createParamVO 入参VO
     * @return ResultStateVO
     */
    ResultStateVO create(CreateParamVO createParamVO);

    /**
     * 根据ID获取组织机构
     *
     * @return ResultStateVO
     */
    ResultStateVO readByID(ReadByIDParamVO readByIDParamVO);

    /**
     * 查询组织机构列表
     *
     * @return ResultStateVO
     */
    ResultStateVO readAll();

    /**
     * 组织机构更新
     *
     * @param updateParamVO 入参VO
     * @return ResultStateVO
     */
    ResultStateVO update(UpdateParamVO updateParamVO);

    /**
     * 组织机构删除
     *
     * @param deleteParamVO 入参VO
     * @return ResultStateVO
     */
    ResultStateVO delete(DeleteParamVO deleteParamVO);
}
