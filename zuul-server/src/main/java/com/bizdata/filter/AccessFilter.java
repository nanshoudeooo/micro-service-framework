package com.bizdata.filter;

import com.bizdata.admin.AdminServiceFeign;
import com.bizdata.properties.TokenProperties;
import com.bizdata.result.ResultStateUtil;
import com.bizdata.token.TokenServiceFeign;
import com.bizdata.url.UrlUtil;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @Autowired
    private AdminServiceFeign adminServiceFeign;

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        // 获取请求路径
        String requestUrl = request.getRequestURI().toString();
        if (UrlUtil.urlAntPathMatchForPatterns(requestUrl, tokenProperties.getIgnoreUrls())) {
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
                    String userID = tokenServiceFeign.getUserIdByToken(token);
                    // 如果存在,则支持访问
                    if (checkUrlInResourceSetting(requestUrl)) {
                        //如果在资源列表中配置过
                        if (authUrl(requestUrl, userID)) {
                            //判断该角色是否能访问
                            // 如果可访问该url
                            accessSuccess(ctx, token);
                        } else {
                            // 如果不可访问该url
                            accessFailure401(ctx, 3, "您无访问该接口权限!");
                        }
                    } else {
                        accessSuccess(ctx, token);
                    }
                }
            }
        }
        return null;
    }

    private void accessSuccess(RequestContext ctx, String token) {
        // 将userID通过header传送到具体应用
        ctx.addZuulRequestHeader("identity-id", tokenServiceFeign.getUserIdByToken(token));
        ctx.setSendZuulResponse(true);
        // 并且自动为该token续租
        tokenServiceFeign.tokenAutoPay(token);
    }


    /**
     * 判断URL是否是资源列表中的配置项,如果是则进入权限判断,如果不是则直接通过
     *
     * @param url
     * @return
     */
    private boolean checkUrlInResourceSetting(String url) {
        List<String> urls = adminServiceFeign.getAllResourceUrl();
        if (UrlUtil.urlAntPathMatchForPatterns(url, urls)) {
            //如果在资源列表中配置过
            return true;
        }
        return false;
    }

    private boolean authUrl(String url, String userID) {
        List<String> urls = new ArrayList<>();
        urls.addAll(adminServiceFeign.getAuthResourceUrl(userID));
        if (UrlUtil.urlAntPathMatchForPatterns(url, urls)) {
            //如果已授权
            return true;
        }
        return false;
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
        ctx.setResponseBody(new Gson().toJson(ResultStateUtil.create(code, msg)));
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

}
