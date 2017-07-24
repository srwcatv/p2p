package com.catv.p2p.controller;

import com.catv.p2p.base.util.AjaxResult;
import com.catv.p2p.business.domain.RechargeOffline;
import com.catv.p2p.business.query.RechargeOfflineQueryObject;
import com.catv.p2p.business.service.IPlatformBankInfoService;
import com.catv.p2p.business.service.IRechargeOfflineService;
import com.catv.p2p.util.RequiredLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 线下充值
 */
@Controller
public class RechargeOfflineController {

    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    @Autowired
    private IRechargeOfflineService rechargeOfflineService;

    /**
     * 充值页面
     * @param model
     * @return
     */
    @RequiredLogin
    @RequestMapping("/recharge")
    public String rechargeOffline(Model model){
        /**
         * 可充值的账户
         */
        model.addAttribute("banks",platformBankInfoService.listAll());
        return "recharge";
    }

    /**
     * 保存线下充值信息
     * @param ro
     * @return
     */
    @RequiredLogin
    @RequestMapping("recharge_save")
    @ResponseBody
    public AjaxResult rechargeSave(RechargeOffline ro){
        int ret = rechargeOfflineService.save(ro);
        if (ret > 0){
            return new AjaxResult("充值成功,请等待后台审核!");
        }
        return new AjaxResult(false,"充值失败");
    }


    @RequiredLogin
    @RequestMapping("recharge_list")
    public String rechargeList(@ModelAttribute("qo") RechargeOfflineQueryObject qo, Model model){
        model.addAttribute("pageResult",rechargeOfflineService.queryPageResult(qo));
        return "recharge_list";
    }
}
