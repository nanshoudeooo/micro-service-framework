package com.bizdata.service;

import com.bizdata.controller.organization.vo.out.OutOrganizationVO;
import com.bizdata.controller.organization.vo.in.InSaveVO;
import com.bizdata.controller.organization.vo.in.InDeleteVO;
import com.bizdata.controller.organization.vo.in.InGetByIdVO;
import com.bizdata.controller.organization.vo.in.InUpdateVO;

import java.util.List;

/**
 * 组织机构Service
 * <p>
 * Created by sdevil507 on 2017/5/9.
 */
public interface OrganizationService {

    /**
     * 组织机构新增
     *
     * @param inSaveVO 入参VO
     * @return true:执行成功;false:执行失败;
     */
    boolean save(InSaveVO inSaveVO);

    /**
     * 根据ID获取组织机构详情
     *
     * @param inGetByIdVO 入参VO
     * @return 组织机构详情
     * @see OutOrganizationVO
     */
    OutOrganizationVO getByID(InGetByIdVO inGetByIdVO);

    /**
     * 查询组织机构列表
     *
     * @return 组织机构详情列表
     * @see OutOrganizationVO
     */
    List<OutOrganizationVO> list();

    /**
     * 组织机构更新
     *
     * @param inUpdateVO 入参VO
     * @return boolean
     */
    boolean update(InUpdateVO inUpdateVO);

    /**
     * 组织机构删除
     *
     * @param inDeleteVO 入参VO
     * @return boolean
     */
    boolean delete(InDeleteVO inDeleteVO);
}
