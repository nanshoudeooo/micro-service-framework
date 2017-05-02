package com.bizdata.service;

/**
 * token令牌生成接口
 * <p>
 * Created by sdevil507 on 2017/4/28.
 */
public interface TokenGenerateService {

    /**
     * 生成唯一token令牌(含前缀)
     *
     * @param prefix 前缀
     * @return String类型token令牌
     */
    String run(String prefix);
}
