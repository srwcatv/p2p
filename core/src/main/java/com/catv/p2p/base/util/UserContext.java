package com.catv.p2p.base.util;

import com.catv.p2p.base.domain.LoginInfo;
import com.catv.p2p.base.vo.VerifyCodeVo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserContext {

    public static final String LOGIN_IN_SESSION = "loginInfo";
    public static final String VERIFYCODEVO_IN_SESSION = "verifyCodeVo";

    public static void putLoginInfo(LoginInfo loginInfo) {
        ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession().setAttribute(LOGIN_IN_SESSION, loginInfo);
    }

    public static void setLoginInfo(LoginInfo loginInfo) {
        ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession().setAttribute(LOGIN_IN_SESSION, loginInfo);
    }

    public static LoginInfo getCurrent() {
        return (LoginInfo) ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession().getAttribute(
                LOGIN_IN_SESSION);
    }

    public static void putVerifyCodeVo(VerifyCodeVo verifyCodeVo) {
        ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession().setAttribute(VERIFYCODEVO_IN_SESSION, verifyCodeVo);
    }

    public static VerifyCodeVo getVerifyCodeVo() {
        return (VerifyCodeVo) ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession().getAttribute(
                VERIFYCODEVO_IN_SESSION);
    }

    public static String getRemoteIp() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getRemoteAddr();
    }
}
