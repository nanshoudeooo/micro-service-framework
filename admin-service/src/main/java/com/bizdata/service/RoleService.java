package com.bizdata.service;

import com.bizdata.entity.Role;
import com.bizdata.jpa.vo.JpaPageParamVO;
import com.bizdata.jpa.vo.JpaSortParamVO;
import com.bizdata.result.ResultStateVO;
import com.bizdata.vo.role.RoleCreateParamVO;
import com.bizdata.vo.role.RoleDeleteParamVO;
import com.bizdata.vo.role.RoleUpdateParamVO;
import org.springframework.data.domain.Page;

/**
 * 角色Service
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
public interface RoleService {

    /**
     * 角色创建
     *
     * @param roleCreateParamVO 角色入参VO
     * @return CodeState类型执行反馈
     */
    ResultStateVO create(RoleCreateParamVO roleCreateParamVO);

    /**
     * 角色更新
     *
     * @param roleUpdateParamVO 入参VO
     * @return CodeState类型执行反馈
     */
    ResultStateVO update(RoleUpdateParamVO roleUpdateParamVO);

    /**
     * 角色删除
     *
     * @param roleDeleteParamVO 入参VO
     * @return boolean执行反馈
     */
    ResultStateVO delete(RoleDeleteParamVO roleDeleteParamVO);

    /**
     * 分页查询所有
     *
     * @param jpaPageParamVO 分页入参VO
     * @param jpaSortParamVO 排序入参VO
     * @return Page<Role>
     */
    Page<Role> findAll(JpaPageParamVO jpaPageParamVO, JpaSortParamVO jpaSortParamVO);
}
