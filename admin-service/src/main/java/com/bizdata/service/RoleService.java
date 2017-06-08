package com.bizdata.service;

import com.bizdata.controller.role.vo.in.InDeleteVO;
import com.bizdata.controller.role.vo.in.InSaveVO;
import com.bizdata.controller.role.vo.in.InUpdateVO;
import com.bizdata.controller.user.vo.out.OutUserVO;
import com.bizdata.po.Role;
import me.sdevil507.vo.JpaPageParamVO;
import me.sdevil507.vo.JpaSortParamVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 角色Service
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
public interface RoleService {

    /**
     * 校验角色名称存在
     *
     * @param roleName 角色名称
     * @return boolean[true:存在,false:不存在]
     */
    boolean checkRoleNameExist(String roleName);

    /**
     * 判断该角色ID是否为系统内置
     *
     * @param roleID 角色ID
     * @return boolean[true:是,false:否]
     */
    boolean checkBuiltInRole(String roleID);

    /**
     * 角色创建
     *
     * @param inSaveVO 角色入参VO
     * @return boolean[true:创建成功,false:创建失败]
     */
    boolean save(InSaveVO inSaveVO);

    /**
     * 角色更新
     *
     * @param inUpdateVO 入参VO
     * @return boolean[true:更新成功,false:更新失败]
     */
    boolean update(InUpdateVO inUpdateVO);

    /**
     * 角色删除
     *
     * @param inDeleteVO 入参VO
     * @return boolean[true:删除成功,false:删除失败]
     */
    boolean delete(InDeleteVO inDeleteVO);

    /**
     * 分页查询所有
     *
     * @param jpaPageParamVO 分页入参VO
     * @param jpaSortParamVO 排序入参VO
     * @return Page<Role>
     */
    Page<Role> list(JpaPageParamVO jpaPageParamVO, JpaSortParamVO jpaSortParamVO);

    /**
     * 根据ID获取所有ROLE列表
     *
     * @param userID 用户ID
     * @return List<Role>
     */
    List<Role> listByUserID(String userID);

    /**
     * 根据角色ID获取角色信息
     *
     * @param roleID 角色ID
     * @return Role
     */
    Role getByID(String roleID);

    /**
     * 根据角色ID和组织机构ID条件进行用户筛选(授权)
     *
     * @param roleID         角色ID
     * @param organizationID 组织机构ID
     * @param words          模糊匹配单词
     * @return List<OutUserVO>
     */
    List<OutUserVO> listAuthorizedUsersByRoleIDAndOrganizationID(String roleID, String organizationID, String words);

    /**
     * 根据角色ID和组织机构ID条件进行用户筛选(未授权)
     *
     * @param roleID         角色ID
     * @param organizationID 组织机构ID
     * @param words          模糊匹配单词
     * @return List<OutUserVO>
     */
    List<OutUserVO> listUnauthorizedUsersByRoleIDAndOrganizationID(String roleID, String organizationID, String words);
}
