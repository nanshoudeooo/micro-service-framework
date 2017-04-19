package com.bizdata.global;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 自定义Spring Cache相关
 * <p>
 * Created by sdevil507 on 2017/4/14.
 */
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    /**
     * 重写keyGenerator()方法,返回自定义key生成
     * 这对于入参参数必须实现toString方法，防止对象入参出现对象名问题
     *
     * @return KeyGenerator
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}
