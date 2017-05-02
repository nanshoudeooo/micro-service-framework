package com.bizdata.auth;

import com.bizdata.token.TokenServiceFeign;
import com.netflix.zuul.context.RequestContext;

/**
 * token令牌校验抽象类
 * <p>
 * Created by sdevil507 on 2017/4/28.
 */
public abstract class TokenAuth {

    protected String prefix = "";

    protected TokenServiceFeign tokenServiceFeign;

    public TokenAuth(TokenServiceFeign tokenServiceFeign) {
        this.tokenServiceFeign = tokenServiceFeign;
    }

    /**
     * 执行校验
     */
    public void run(RequestContext requestContext, String token) {
        //判断token的真实性与时效性校验
        if (!tokenServiceFeign.checkTokenExist(token)) {
            // 如果不存在该token,说明为过期或者伪造
            AccessStateHelper.accessFailure401(requestContext, 3, "提供的token无效或已过期!");
        } else {
            //如果存在该token,执行细化的具体验证,由各子类实现
            tokenAvailable(requestContext, token);
        }
    }

    /**
     * 当前token可用
     */
    protected abstract void tokenAvailable(RequestContext requestContext, String token);

    public String getPrefix() {
        return prefix;
    }
}
