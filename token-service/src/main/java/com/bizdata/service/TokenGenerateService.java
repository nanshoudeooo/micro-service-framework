package com.bizdata.service;

/**
 * token令牌生成接口
 *
 * Created by sdevil507 on 2017/4/28.
 */
public interface TokenGenerateService {

    /**
     * 生成唯一token令牌
     *
     * @return String类型token令牌
     */
    public String run();
}
