package com.catv.p2p.mgrsite.controller;

import com.catv.p2p.base.domain.RealAuth;
import com.catv.p2p.base.query.RealAuthQueryObject;
import com.catv.p2p.base.service.IRealAuthService;
import com.catv.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 实名认证审核
 */
@Controller
public class MgrSiteRealAuthController {

    @Autowired
    private IRealAuthService realAuthService;

    /**
     * 实名认证审核列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("realAuth")
    public String realAuth(@ModelAttribute("qo") RealAuthQueryObject qo, Model model){
        model.addAttribute("pageResult",realAuthService.queryPageResult(qo));
        return "/realAuth/list";
    }

    /**
     * 提交审核
     * @param realAuth
     * @return
     */
    @RequestMapping("/realAuth_audit")
    @ResponseBody
    public AjaxResult realAuthAudit(RealAuth realAuth){
        int ret = realAuthService.audit(realAuth);
        if (ret >0) {
            return new AjaxResult("审核提交成功");
        } {
            return new AjaxResult(false,"审核提交失败");
        }
    }
}
