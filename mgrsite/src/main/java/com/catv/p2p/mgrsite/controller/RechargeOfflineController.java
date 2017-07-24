package com.catv.p2p.mgrsite.controller;

import com.catv.p2p.base.util.AjaxResult;
import com.catv.p2p.business.query.RechargeOfflineQueryObject;
import com.catv.p2p.business.service.IPlatformBankInfoService;
import com.catv.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 线下充值审核
 */
@Controller
public class RechargeOfflineController {

    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    @Autowired
    private IRechargeOfflineService rechargeOfflineService;

    /**
     * 线下充值审核列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("rechargeOffline")
    public String RechargeOfflineList(@ModelAttribute("qo") RechargeOfflineQueryObject qo, Model model){
        System.out.println(qo.getTradeCode());
        model.addAttribute("banks",platformBankInfoService.listAll());
        model.addAttribute("pageResult",rechargeOfflineService.queryPageResult(qo));
        return "/rechargeOffline/list";
    }

    /**
     * 线下充值审核
     */
    @RequestMapping("rechargeOffline_audit")
    @ResponseBody
    public AjaxResult audit(Long id, String remark, int state) {
        this.rechargeOfflineService.audit(id, remark, state);
        return new AjaxResult();
    }
}
