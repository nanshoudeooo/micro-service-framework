package com.bizdata.auth;

import com.bizdata.result.ResultStateUtil;
import com.google.gson.Gson;
import com.netflix.zuul.context.RequestContext;

/**
 * 访问状态帮助类
 * <p>
 * Created by sdevil507 on 2017/4/29.
 */
public class AccessStateHelper {

    /**
     * 访问失败情况resp返回
     *
     * @param code 状态码
     * @param msg  消息
     */
    public static void accessFailure401(RequestContext requestContext, int code, String msg) {
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(401);
        requestContext.getResponse().setCharacterEncoding("UTF-8");
        requestContext.setResponseBody(new Gson().toJson(ResultStateUtil.create(code, msg)));
    }

    /**
     * token令牌校验通过执行转发请求,并将userID传递
     */
    public static void accessSuccess(RequestContext requestContext,String userID) {
        // 将userID通过header传送到具体应用
        requestContext.addZuulRequestHeader("identity-id", userID);
        requestContext.setSendZuulResponse(true);
    }
}
