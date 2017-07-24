package com.catv.p2p.mgrsite.controller;

import com.catv.p2p.base.domain.LoginInfo;
import com.catv.p2p.base.service.ILoginInfoService;
import com.catv.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 后台登陆
 */
@Controller
public class MgrSiteLoginController {

    @Autowired
    private ILoginInfoService loginInfoService;

    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(String username, String password){
        AjaxResult ajaxResult = new AjaxResult();
        LoginInfo loginInfo = loginInfoService.login(username,password,LoginInfo.USER_MANAGER);
        if (loginInfo == null) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("用户名或密码错误");
        }
        return ajaxResult;
    }


    @RequestMapping("/index")
    public String index(){
        return "main";
    }

    @RequestMapping("/mgrSiteLogout")
    public String mgrSiteLogout(HttpServletRequest request){
        Enumeration enumeration = request.getSession().getAttributeNames();
        while (enumeration.hasMoreElements()){
            request.getSession().removeAttribute(enumeration.nextElement().toString());
        }
        return "redirect:login.html";
    }
}
