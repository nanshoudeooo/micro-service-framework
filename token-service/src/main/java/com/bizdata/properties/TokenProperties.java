package com.bizdata.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * token相关配置
 *
 * @author sdevil507
 *
 */
@Component
@ConfigurationProperties(prefix = "token")
public class TokenProperties {

	/**
	 * token过期时间
	 */
	private int expire;

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

}
