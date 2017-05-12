package com.bizdata.service;

import com.bizdata.po.User;
import com.bizdata.jpa.vo.JpaPageParamVO;
import com.bizdata.jpa.vo.JpaSortParamVO;
import com.bizdata.result.ResultStateVO;
import com.bizdata.controller.user.vo.in.InSaveVO;
import com.bizdata.controller.user.vo.in.InSearchVO;
import com.bizdata.controller.user.vo.in.InUpdateVO;
import org.springframework.data.domain.Page;

/**
 * 用户操作相关接口
 * <p>
 * Created by sdevil507 on 2017/4/7.
 */
public interface UserService {

    /**
     * 执行登录操作
     *
     * @param username 用户名
     * @param password 密码
     * @return String类型token值
     */
    ResultStateVO login(String username, String password);

    /**
     * 执行注销
     *
     * @param token token值
     * @return boolean执行反馈
     */
    ResultStateVO logout(String token);

    /**
     * 用户新增
     *
     * @param inSaveVO 入参VO
     * @param userID   创建者ID
     * @return boolean
     */
    boolean save(InSaveVO inSaveVO, String userID);

    /**
     * 执行用户删除操作
     *
     * @param currentUserID 当前用户ID
     * @param userID        用户ID
     * @return boolean执行反馈
     */
    ResultStateVO delete(String currentUserID, String userID);

    /**
     * 执行用户更新操作
     *
     * @param inUpdateVO User类型对象
     * @return boolean执行反馈
     */
    ResultStateVO update(InUpdateVO inUpdateVO);

    /**
     * 执行分页查询用户
     *
     * @param jpaPageInputVO        分页参数VO
     * @param jpaSortParamVO        排序入参VO
     * @param inSearchVO 查询条件VO
     * @return Page类型用户数据
     */
    Page<User> list(JpaPageParamVO jpaPageInputVO, JpaSortParamVO jpaSortParamVO, InSearchVO inSearchVO);

    /**
     * 根据用户ID获取用户信息
     *
     * @param userID 用户ID
     * @return User
     */
    User getByID(String userID);

    /**
     * 验证用户名是否重复
     *
     * @param username 用户名
     * @return boolean执行反馈
     */
    boolean validDuplicateUsername(String username);

    /**
     * 重置用户密码
     *
     * @param userID   用户ID
     * @param password 密码
     * @return boolean执行反馈
     */
    boolean resetPassword(String userID, String password);

}
