package com.catv.p2p.controller;

import com.catv.p2p.base.domain.UserInfo;
import com.catv.p2p.base.query.IpLogQueryObject;
import com.catv.p2p.base.service.IIpLogService;
import com.catv.p2p.base.service.ISystemDictionaryService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.service.IVerifyCodeService;
import com.catv.p2p.base.util.AjaxResult;
import com.catv.p2p.base.util.UserContext;
import com.catv.p2p.util.RequiredLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 个人中心
 */
@Controller
public class PersonalController {

    @Autowired
    private IIpLogService ipLogService;

    @Autowired
    private IVerifyCodeService verifyCodeService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    /**
     * 登陆日志列表
     *
     * @param qo    查询对象
     * @param model 传递给前台的参数
     * @return
     */
    @RequiredLogin
    @RequestMapping("/ipLog")
    public String ipLog(@ModelAttribute("qo") IpLogQueryObject qo, Model model) {
        qo.setUsername(UserContext.getCurrent().getUsername());
        model.addAttribute("result", ipLogService.queryPageResult(qo));
        return "iplog_list";
    }

    /**
     * 手机发送验证码
     *
     * @param phoneNumber 手机号
     * @return
     */
    @RequiredLogin
    @RequestMapping("/sendVerifyCode")
    @ResponseBody
    public AjaxResult sendVerifyCode(String phoneNumber) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            verifyCodeService.sendVerifyCode(phoneNumber);
        } catch (RuntimeException e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg(e.getMessage());
        }
        return ajaxResult;
    }

    /**
     * 绑定手机号
     *
     * @param phoneNumber 手机号
     * @param code        验证码
     * @return
     */
    @RequiredLogin
    @RequestMapping("/bindPhone")
    @ResponseBody
    public AjaxResult bindPhone(String phoneNumber, String code) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            userInfoService.bindPhone(phoneNumber, code);
        } catch (RuntimeException e) {
            ajaxResult.setMsg(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }

    /**
     * 绑定手机号
     *
     * @param email 邮箱地址
     * @return
     */
    @RequiredLogin
    @RequestMapping("/sendEmail")
    @ResponseBody
    public AjaxResult sendEmail(String email) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            verifyCodeService.sendEmail(email);
        } catch (RuntimeException e) {
            ajaxResult.setMsg(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }

    /**
     * 确认绑定邮箱
     *
     * @param uuid  提交上来的uuid
     * @param model 设置返回参数
     * @return
     */
    @RequestMapping("checkBindEmail")
    public String checkBindEmail(String uuid, Model model) {
        try {
            userInfoService.bindEmail(uuid);
            model.addAttribute("success", true);
        } catch (RuntimeException e) {
            model.addAttribute("success", false);
            model.addAttribute("msg", e.getMessage());
        }
        return "checkmail_result";
    }

    /**
     * 用户基本资料
     *
     * @param model
     * @return
     */
    @RequiredLogin
    @RequestMapping("/basicInfo")
    public String basicInfo(Model model) {
        //当前登陆用户信息
        model.addAttribute("userInfo", userInfoService.getCurrent());
        model.addAttribute("educationBackgrounds", systemDictionaryService.queryListBySn("educationBackground"));
        model.addAttribute("incomeGrades", systemDictionaryService.queryListBySn("incomeGrade"));
        model.addAttribute("marriages", systemDictionaryService.queryListBySn("marriage"));
        model.addAttribute("kidCounts", systemDictionaryService.queryListBySn("kidCount"));
        model.addAttribute("houseConditions", systemDictionaryService.queryListBySn("houseCondition"));
        return "userInfo";
    }

    /**
     * 保存用户基本资料
     *
     * @param userInfo
     * @return
     */
    @RequestMapping("/basicInfo_save")
    @ResponseBody
    public AjaxResult basicInfoSave(UserInfo userInfo) {
        int ret = userInfoService.updateBasicInfo(userInfo);
        if (ret > 0) {
            return new AjaxResult("保存成功");
        } else {
            return new AjaxResult(false,"保存失败");
        }
    }
}
