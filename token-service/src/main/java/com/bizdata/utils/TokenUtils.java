package com.bizdata.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * token相关工具类
 *
 * @author sdevil507
 *
 */
@Component
public class TokenUtils {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 生成全局唯一token
	 * 
	 * @return
	 */
	public String generateToken() {
		String token;
		while (true) {
			String tempToken = UUID.randomUUID().toString();
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
