package com.catv.p2p.mgrsite.controller;

import com.catv.p2p.base.domain.UserFile;
import com.catv.p2p.base.domain.UserInfo;
import com.catv.p2p.base.query.UserFileQueryObject;
import com.catv.p2p.base.service.IRealAuthService;
import com.catv.p2p.base.service.IUserFileService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.util.AjaxResult;
import com.catv.p2p.base.util.BidConst;
import com.catv.p2p.business.domain.BidRequest;
import com.catv.p2p.business.query.BidRequestQueryObject;
import com.catv.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 申请借款审核
 */
@Controller
public class MgrSiteBidRequestAuditController {

    @Autowired
    private IBidRequestService bidRequestService;
    
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IRealAuthService realAuthService;

    @Autowired
    private IUserFileService userFileService;

    /**
     * 发标前审核列表
     * @param model
     * @return
     */
    @RequestMapping("/bidRequest_publishAudit_list")
    public String bidRequestPublishAuditList(BidRequestQueryObject qo, Model model){
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
        model.addAttribute("pageResult",bidRequestService.queryPageResult(qo));
        return "bidrequest/publish_audit";
    }

    /**
     * 发表前审核提交
     * @param id
     * @param state
     * @param remark
     * @return
     */
    @RequestMapping("/bidrequest_publishaudit")
    @ResponseBody
    public AjaxResult bidRequestPublishAudit(Long id,int state,String remark){
        try {
            bidRequestService.publishAudit(id, state, remark);
            return new AjaxResult("审核成功");
        }catch (RuntimeException e){
            e.printStackTrace();
            return new AjaxResult(false,"审核失败"+e.getMessage());
        }
    }

    /**
     * 后台查看借款详情
     */
    @RequestMapping("borrow_info")
    public String borrowInfoDetail(Long id, Model model) {
        // bidRequest;
        BidRequest bidRequest = this.bidRequestService.get(id);
        UserInfo userinfo = this.userInfoService.getByPrimaryKey(bidRequest.getCreateUser()
                .getId());
        model.addAttribute("bidRequest", bidRequest);
        // userInfo
        model.addAttribute("userInfo", userinfo);
        // audits:这个标的审核历史
        model.addAttribute("audits",
                this.bidRequestService.listAuditHistoryByBidRequest(id));
        // realAuth:借款人实名认证信息
        model.addAttribute("realAuth",
                this.realAuthService.query(userinfo.getRealAuthId()));
        // userFiles:该借款人的风控资料信息
        UserFileQueryObject qo = new UserFileQueryObject();
        qo.setApplierId(userinfo.getId());
        qo.setState(UserFile.STATE_AUDIT);
        qo.setPageSize(-1);
        model.addAttribute("userFiles", this.userFileService.queryForList(qo));
        return "bidrequest/borrow_info";
    }
}
