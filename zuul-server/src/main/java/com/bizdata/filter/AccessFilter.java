package com.bizdata.filter;

import com.bizdata.properties.TokenProperties;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.token.TokenServiceFeign;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 访问授权过滤器
 *
 * @author sdevil507
 */
public class AccessFilter extends ZuulFilter {

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private TokenServiceFeign tokenServiceFeign;

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        // 获取请求路径
        String requestUrl = request.getRequestURI().toString();
        if (urlWithNoNeedForAuth(requestUrl, tokenProperties.getIgnoreUrls())) {
            // 如果该请求路径不需要鉴权,直接通过。(例如获取token,登录等...)
        } else {
            // 如果需要鉴权
            // 首先判断请求header是否携带token
            String token = request.getHeader("token");
            if (null == token) {
                // 如果不存在token,拦截请求,并返回相应提示
                accessFailure401(ctx, 1, "未提供token,无权访问!");
            } else {
                // 存在token,需要校验token是否有效,调用token微服务获取
                if (!tokenServiceFeign.checkTokenExist(token)) {
                    // 如果不存在该token,说明为过期或者伪造
                    accessFailure401(ctx, 2, "提供的token无效或已过期!");
                } else {
                    // 如果存在,则支持访问
                    // TODO 此处需要根据权限动态支持访问
                    // 将userID通过header传送到具体应用
                    ctx.addZuulRequestHeader("identity-id",tokenServiceFeign.getUserIdByToken(token));
                    ctx.setSendZuulResponse(true);
                    // 并且自动为该token续租
                    tokenServiceFeign.tokenAutoPay(token);
                }
            }
        }
        return null;
    }

    private HttpServletRequestWrapper modifyRequest(HttpServletRequest request){
        return new HttpServletRequestWrapper(request);
    }

    /**
     * 访问失败情况resp返回
     *
     * @param ctx  RequestContext
     * @param code 状态码
     * @param msg  消息
     */
    private void accessFailure401(RequestContext ctx, int code, String msg) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        ctx.getResponse().setCharacterEncoding("UTF-8");
        ctx.setResponseBody(new Gson().toJson(ResultStateUtil.create(code,msg)));
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 判断url是否不过滤
     *
     * @param requestUrl 请求路径
     * @param ignoreUrl  忽略路径
     * @return boolean 忽略结果
     */
    private boolean ignoreUrlMatch(String requestUrl, String ignoreUrl) {
        PathMatcher matcher = new AntPathMatcher();
        boolean result = matcher.match(ignoreUrl, requestUrl);
        return result;
    }

    /**
     * 判断访问该url不需要授权
     *
     * @param requestUrl 请求url
     * @param pattens    path规则
     * @return boolean
     */
    private boolean urlWithNoNeedForAuth(String requestUrl, List<String> pattens) {
        for (String url : pattens) {
            if (ignoreUrlMatch(requestUrl, url)) {
                return true;
            }
        }
        return false;
    }
}
