package com.catv.p2p.mgrsite.Interceptor;

import com.catv.p2p.base.domain.LoginInfo;
import com.catv.p2p.base.util.UserContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台登陆拦截
 */
public class MgrSiteLoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginInfo current = UserContext.getCurrent();
        if (current == null) {
            response.sendRedirect("/login.html");
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
