package com.bizdata.service;

import com.bizdata.po.Role;
import com.bizdata.jpa.vo.JpaPageParamVO;
import com.bizdata.jpa.vo.JpaSortParamVO;
import com.bizdata.result.ResultStateVO;
import com.bizdata.vo.role.RoleCreateParamVO;
import com.bizdata.vo.role.RoleDeleteParamVO;
import com.bizdata.vo.role.RoleUpdateParamVO;
import org.springframework.data.domain.Page;

import java.util.List;

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

    /**
     * 根据ID获取所有ROLE列表
     *
     * @param userID 用户ID
     * @return List<Role>
     */
    List<Role> findAllByUserID(String userID);

    /**
     * 根据角色ID获取角色信息
     *
     * @param roleID 角色ID
     * @return Role
     */
    Role findOne(String roleID);
}
