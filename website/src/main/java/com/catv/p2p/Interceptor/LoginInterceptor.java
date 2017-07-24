package com.catv.p2p.Interceptor;

import com.catv.p2p.base.util.UserContext;
import com.catv.p2p.util.RequiredLogin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 *  访问拦截器
 */
class AccessInterceptor extends HandlerInterceptorAdapter {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Annotation annotation = handlerMethod.getMethodAnnotation(RequiredLogin.class);
            if (annotation != null && UserContext.getCurrent() == null){
                response.sendRedirect("/login.html");
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }
}
