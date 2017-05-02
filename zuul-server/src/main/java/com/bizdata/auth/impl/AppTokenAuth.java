package com.bizdata.auth.impl;

import com.bizdata.auth.AccessStateHelper;
import com.bizdata.auth.TokenAuth;
import com.bizdata.token.TokenServiceFeign;
import com.netflix.zuul.context.RequestContext;

/**
 * app端token校验实现
 * <p>
 * Created by sdevil507 on 2017/4/28.
 */
public class AppTokenAuth extends TokenAuth {

    public AppTokenAuth(String prefix, String urlPattern, TokenServiceFeign tokenServiceFeign) {
        super(prefix, urlPattern, tokenServiceFeign);
    }

    @Override
    protected void tokenAvailable(RequestContext requestContext, String token) {
        //app当前规则,token存在且未失效则通过校验可以访问接口
        AccessStateHelper.accessSuccess(requestContext, tokenServiceFeign.getUserIdByToken(token));
        // 并且自动为该token续租
        tokenServiceFeign.tokenAutoPay(token);
    }
}
