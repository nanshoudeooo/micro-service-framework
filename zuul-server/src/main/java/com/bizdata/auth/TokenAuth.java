package com.bizdata.auth;

import com.bizdata.token.TokenServiceFeign;
import com.bizdata.url.UrlUtil;
import com.netflix.zuul.context.RequestContext;

/**
 * token令牌校验抽象类
 * <p>
 * Created by sdevil507 on 2017/4/28.
 */
public abstract class TokenAuth {

    /**
     * 校验器拦截的url(用于判断请求是否属于该校验器)
     */
    protected String[] urlPatterns;

    /**
     * token前缀,用于区分对应鉴权实现
     */
    protected String prefix = "";

    /**
     * token服务提供
     */
    protected TokenServiceFeign tokenServiceFeign;

    public TokenAuth(String prefix, String[] urlPatterns, TokenServiceFeign tokenServiceFeign) {
        this.prefix = prefix;
        this.urlPatterns = urlPatterns;
        this.tokenServiceFeign = tokenServiceFeign;
    }

    /**
     * 执行校验
     */
    public void run(RequestContext requestContext, String token) {
        // 判断token的真实性与时效性校验
        if (!tokenServiceFeign.checkTokenExist(token)) {
            // 如果不存在该token,说明为过期或者伪造
            AccessStateHelper.accessFailure401(requestContext, 3, "提供的token无效或已过期!");
        } else {
            //如果存在该token,执行细化的具体验证,由各子类实现
            // 获取当前访问路径
            String currentUrl = requestContext.getRequest().getRequestURI().toString();
            if (UrlUtil.urlAntPathMatchForPatterns(currentUrl, urlPatterns)) {
                //如果当前请求符合校验器url规则
                tokenAvailable(requestContext, token);
            } else {
                //如果不符合当前校验器url校验规则
                AccessStateHelper.accessFailure401(requestContext, 4, "您无访问该接口权限!");
            }
        }
    }

    /**
     * 当前token可用执行逻辑
     */
    protected abstract void tokenAvailable(RequestContext requestContext, String token);

    public String getPrefix() {
        return prefix;
    }
}
