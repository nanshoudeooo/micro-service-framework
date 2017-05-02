package com.bizdata.auth;

import com.bizdata.url.UrlUtil;
import com.netflix.zuul.context.RequestContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 鉴权管理器,分配具体鉴权操作
 * <p>
 * Created by sdevil507 on 2017/4/28.
 */
public class AuthManager {

    private RequestContext requestContext;

    /**
     * 需要忽略的url列表
     */
    private List<String> ignoreUrls;

    public AuthManager(RequestContext requestContext, List<String> ignoreUrls) {
        this.requestContext = requestContext;
        this.ignoreUrls = ignoreUrls;
    }

    Map<String, TokenAuth> authMap = new HashMap<>();

    /**
     * 根据token前缀获取对应的鉴权执行者
     *
     * @return TokenAuth
     */
    private TokenAuth getAuthByPrefix(String token) {
        String prefix = token.split(":")[0];
        return authMap.get(prefix);
    }

    /**
     * 添加具体鉴权类
     *
     * @param tokenAuth
     */
    public AuthManager addAuthImpl(TokenAuth tokenAuth) {
        authMap.put(tokenAuth.getPrefix(), tokenAuth);
        return this;
    }

    /**
     * 执行校验
     */
    public void doAuth() {
        String requestUrl = requestContext.getRequest().getRequestURI().toString();
        if (UrlUtil.urlAntPathMatchForPatterns(requestUrl, ignoreUrls)) {
            // 如果该请求路径不需要提供token,直接放行请求。(例如登录等...)
        } else {
            // 如果需要鉴权,根据token的前缀判断执行哪个鉴权子类
            String token = requestContext.getRequest().getHeader("token");
            //首选判断token是否提交
            if (null == token) {
                //如果未提交
                AccessStateHelper.accessFailure401(requestContext, 1, "未提供token,无权访问!");
            } else {
                //如果提交了,对应找到各自的鉴权类
                TokenAuth tokenAuth = getAuthByPrefix(token);
                if (null != tokenAuth) {
                    tokenAuth.run(requestContext, token);
                } else {
                    //如果提供token未含前缀或未找到匹配的验证器,说明为伪造则提示参数不合法
                    AccessStateHelper.accessFailure401(requestContext, 2, "token参数不合法!");
                }
            }
        }
    }
}
