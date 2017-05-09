package com.bizdata.dao;

import com.bizdata.entity.User;
import com.bizdata.jpa.base.JpaBaseRepository;

/**
 * 用户Dao
 * <p>
 * Created by sdevil507 on 2017/4/7.
 */
public interface UserDao extends JpaBaseRepository<User, String> {

    /**
     * 根据用户名密码查找用户
     *
     * @param username 用户名
     * @param password 密码
     * @return User对象
     */
    User findByUsernameAndPassword(String username, String password);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return User对象
     */
    User findByUsername(String username);
}
