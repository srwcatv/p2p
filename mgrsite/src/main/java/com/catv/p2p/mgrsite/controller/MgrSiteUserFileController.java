package com.catv.p2p.mgrsite.controller;

import com.catv.p2p.base.domain.UserFile;
import com.catv.p2p.base.query.UserFileQueryObject;
import com.catv.p2p.base.service.IUserFileService;
import com.catv.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 风控资料审核
 */
@Controller
public class MgrSiteUserFileController {

    @Autowired
    private IUserFileService userFileService;

    /**
     * 风控资料分页列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("/userFileAuth")
    public String userFileList(@ModelAttribute("qo")UserFileQueryObject qo, Model model){
        model.addAttribute("pageResult",userFileService.getPageResult(qo));
        return "/userFileAuth/list";
    }

    /**
     * 提交审核
     * @param userFile
     * @return
     */
    @RequestMapping("/userFile_audit")
    @ResponseBody
    public AjaxResult userFileAudit(UserFile userFile){
        userFileService.audit(userFile);
        return new AjaxResult();
    }
}
