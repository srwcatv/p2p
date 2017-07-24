package com.catv.p2p.controller;

import com.catv.p2p.base.service.ILoginInfoService;
import com.catv.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户注册控制器
 */
@Controller
public class RegisterController {

    @Autowired
    private ILoginInfoService loginInfoService;

    @RequestMapping("/register")
    @ResponseBody
    public AjaxResult register(String username,String password){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            loginInfoService.register(username,password);
        } catch (RuntimeException e) {
            ajaxResult.setMsg(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }

    @RequestMapping("/checkUsername")
    @ResponseBody
    public boolean checkUsername(String username){
        return loginInfoService.checkUsername(username);
    }
}
