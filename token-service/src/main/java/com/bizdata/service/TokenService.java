package com.bizdata.service;

/**
 * TokenService接口
 *
 * @author sdevil507
 */
public interface TokenService {

    /**
     * 传入userID创建token
     *
     * @param prefix 类别前缀
     * @param userID 用户ID
     * @return String类型token字符串
     */
    String createToken(String prefix, String userID);

    /**
     * 注销token
     *
     * @param tokenID tokenID
     * @return
     */
    boolean removeToken(String tokenID);

    /**
     * token时间续租
     *
     * @param token token
     * @return
     */
    boolean tokenAutoPay(String token);

    /**
     * 根据token获取userID
     *
     * @param token token
     * @return
     */
    String getUserID(String token);

    /**
     * 判断token是否存在
     *
     * @param token token
     * @return
     */
    boolean checkTokenExist(String token);

}
