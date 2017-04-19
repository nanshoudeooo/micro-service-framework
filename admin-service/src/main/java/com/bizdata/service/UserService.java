package com.bizdata.service;

import com.bizdata.entity.User;
import com.bizdata.jpa.vo.JpaPageParamVO;
import com.bizdata.jpa.vo.JpaSortParamVO;
import com.bizdata.result.ResultStateVO;
import com.bizdata.vo.user.UserCreateParamVO;
import com.bizdata.vo.user.UserReadOneParamVO;
import com.bizdata.vo.user.UserUpdateParamVO;
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
     * 执行创建用户操作
     *
     * @param vo     用户
     * @param userID 用户ID用于设置creator
     * @return BoolState执行反馈
     */
    ResultStateVO create(UserCreateParamVO vo, String userID);

    /**
     * 执行用户删除操作
     *
     *
     * @param currentUserID
     * @param userID 用户ID
     * @return boolean执行反馈
     */
    ResultStateVO delete(String currentUserID, String userID);

    /**
     * 执行用户更新操作
     *
     * @param userUpdateParamVO User类型对象
     * @return boolean执行反馈
     */
    ResultStateVO update(UserUpdateParamVO userUpdateParamVO);

    /**
     * 执行分页查询用户
     *
     * @param jpaPageInputVO 分页参数VO
     * @return Page类型用户数据
     */
    Page<User> findAll(JpaPageParamVO jpaPageInputVO, JpaSortParamVO jpaSortParamVO);

    /**
     * 根据用户ID获取用户信息
     *
     * @param userReadOneParamVO 查询单个用户入参
     * @return User
     */
    User findOne(UserReadOneParamVO userReadOneParamVO);

}
