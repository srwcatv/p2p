package com.catv.p2p.mgrsite.controller;

import com.catv.p2p.base.query.VedioAuthQueryObject;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.service.IVedioAuthService;
import com.catv.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 视频认证
 */
@Controller
public class MgrSiteVedioAuthController {

    @Autowired
    private IVedioAuthService vedioAuthService;

    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 视频认证列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("vedioAuth")
    public String vedioAuth(@ModelAttribute("qo")VedioAuthQueryObject qo, Model model){
        model.addAttribute("pageResult",vedioAuthService.queryPageResult(qo));
        return "/vedioAuth/list";
    }

    /**
     * 视频审核
     * @param loginInfoValue
     * @param remark
     * @return
     */
    @RequestMapping("vedioAuth_audit")
    @ResponseBody
    public AjaxResult audit(Long loginInfoValue,String remark,int state){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            vedioAuthService.audit(loginInfoValue,remark,state);
            ajaxResult.setMsg("审核成功");
        } catch (RuntimeException e){
            ajaxResult.setMsg(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }

    /**
     * 自动补全下拉列表
     * @param keyword
     * @return
     */
    @RequestMapping("/vedioAuth_autocomplate")
    @ResponseBody
    public List<Map<String,Object>> vedioAuthAutocomplate(String keyword){
        return userInfoService.getList(keyword);
    }
}
