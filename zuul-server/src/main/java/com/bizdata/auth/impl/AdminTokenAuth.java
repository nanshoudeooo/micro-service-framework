package com.bizdata.auth.impl;

import com.bizdata.admin.AdminServiceFeign;
import com.bizdata.auth.AccessStateHelper;
import com.bizdata.auth.TokenAuth;
import com.bizdata.token.TokenServiceFeign;
import com.bizdata.url.UrlUtil;
import com.netflix.zuul.context.RequestContext;

import java.util.List;

/**
 * admin后台管理token校验实现
 * <p>
 * Created by sdevil507 on 2017/4/28.
 */
public class AdminTokenAuth extends TokenAuth {

    private AdminServiceFeign adminServiceFeign;

    public AdminTokenAuth(String prefix, String[] urlPatterns, TokenServiceFeign tokenServiceFeign, AdminServiceFeign adminServiceFeign) {
        super(prefix, urlPatterns, tokenServiceFeign);
        this.adminServiceFeign = adminServiceFeign;
    }

    @Override
    protected void tokenAvailable(RequestContext requestContext, String token) {
        String requestUrl = requestContext.getRequest().getRequestURI().toString();
        String userID = tokenServiceFeign.getUserIdByToken(token);
        if (checkUrlInResourceSetting(requestUrl)) {
            //如果是需要鉴权的url
            if (authUrl(requestUrl, userID)) {
                //判断该角色是否能访问
                // 如果可访问该url
                AccessStateHelper.accessSuccess(requestContext, userID);
                // 并且自动为该token续租
                tokenServiceFeign.tokenAutoPay(token);
            } else {
                // 如果不可访问该url
                AccessStateHelper.accessFailure401(requestContext, 4, "您无访问该接口权限!");
            }
        } else {
            //如果是不需要鉴权的url
            AccessStateHelper.accessSuccess(requestContext, userID);
            // 并且自动为该token续租
            tokenServiceFeign.tokenAutoPay(token);
        }
    }

    /**
     * 判断URL是否是资源列表中的配置项,如果是则进入权限判断,如果不是则直接通过
     *
     * @param url 请求url
     * @return boolean类型执行结果
     */
    private boolean checkUrlInResourceSetting(String url) {
        List<String> urls = adminServiceFeign.listResourceUrl();
        return false;
    }

    /**
     * 判断获取当前用户授权url列表
     *
     * @param url    请求url
     * @param userID 用户Id
     * @return boolean类型执行反馈
     */
    private boolean authUrl(String url, String userID) {
        List<String> urls = adminServiceFeign.listAuthUrl(userID);
        if (UrlUtil.urlAntPathMatchForPatterns(url, urls)) {
            //如果已授权
            return true;
        }
        return false;
    }
}
