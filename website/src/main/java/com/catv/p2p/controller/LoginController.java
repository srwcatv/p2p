package com.catv.p2p.controller;

import com.catv.p2p.base.domain.LoginInfo;
import com.catv.p2p.base.service.IAccountService;
import com.catv.p2p.base.service.ILoginInfoService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.util.AjaxResult;
import com.catv.p2p.base.util.UserContext;
import com.catv.p2p.util.RequiredLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登陆
 */
@Controller
public class LoginController {

    @Autowired
    private ILoginInfoService loginInfoService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IAccountService accountService;

    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(String username, String password) {
        AjaxResult ajaxResult = new AjaxResult();
        LoginInfo loginInfo = loginInfoService.login(username, password, LoginInfo.USER_CLIENT);
        if (loginInfo == null) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("用户名或密码不正确");
        }
        return ajaxResult;
    }

    @RequiredLogin
    @RequestMapping("/personal")
    public String personal(Model model) {
        LoginInfo loginInfo = UserContext.getCurrent();
        model.addAttribute("account", accountService.getByPrimaryKey(loginInfo.getId()));
        model.addAttribute("userInfo", userInfoService.getByPrimaryKey(loginInfo.getId()));
        return "personal";
    }

    @RequestMapping("/logout")
    public String logout() {
        UserContext.setLoginInfo(null);
        return "redirect:personal.do";
    }
}
