package com.bizdata.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token")
public class TokenProperties {
	/**
	 * 不用鉴权的url列表
	 */
	private List<String> ignoreUrls = new ArrayList<>();

	public List<String> getIgnoreUrls() {
		return ignoreUrls;
	}

	public void setIgnoreUrls(List<String> ignoreUrls) {
		this.ignoreUrls = ignoreUrls;
	}

}
