package com.bizdata.service.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.bizdata.properties.TokenProperties;
import com.bizdata.service.TokenService;
import com.bizdata.utils.TokenUtils;

@Service
public class TokenServiceImpl implements TokenService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private TokenProperties tokenProperties;

	@Override
	public String createToken(String userID) {
		String token = "";
		try {
			token = tokenUtils.generateToken();
			stringRedisTemplate.opsForValue().set(token, userID, tokenProperties.getExpire(), TimeUnit.MINUTES);
		} catch (Exception e) {
			logger.error("token create error:", e);
		}
		return token;
	}

	@Override
	public boolean removeToken(String token) {
		boolean state = false;
		try {
			stringRedisTemplate.delete(token);
			state = true;
		} catch (Exception e) {
			logger.error("token delete error:", e);
		}
		return state;
	}

	@Override
	public boolean tokenAutoPay(String token) {
		boolean state = false;
		try {
			stringRedisTemplate.expire(token, tokenProperties.getExpire(), TimeUnit.MINUTES);
			state = true;
		} catch (Exception e) {
			logger.error("token autoPay error:", e);
		}
		return state;
	}

	@Override
	public String getUserID(String token) {
		String userID = "";
		try {
			userID = stringRedisTemplate.opsForValue().get(token);
		} catch (Exception e) {
			logger.error("get userID error:", e);
		}
		return userID;
	}

	@Override
	public boolean checkTokenExist(String token) {
		boolean state = false;
		String value = stringRedisTemplate.opsForValue().get(token);
		if (null != value) {
			state = true;
		}
		return state;
	}
}
