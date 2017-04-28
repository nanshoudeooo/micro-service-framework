package com.bizdata.service.impl;

import com.bizdata.service.TokenGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * token令牌生成redis实现
 * <p>
 * Created by sdevil507 on 2017/4/28.
 */
@Service
public class TokenGenerateServiceRedisImpl implements TokenGenerateService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String run(String prefix) {
        String token;
        while (true) {
            String tempToken = prefix + UUID.randomUUID().toString();
            // 判断redis中是否已存在,防止重复
            String result = stringRedisTemplate.opsForValue().get(tempToken);
            // 如果redis中不存在,则该token可用,执行返回
            if (null == result) {
                token = tempToken;
                return token;
            }
        }
    }
}
