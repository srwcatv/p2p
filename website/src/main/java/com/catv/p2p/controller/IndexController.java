package com.catv.p2p.controller;

import com.catv.p2p.base.domain.UserFile;
import com.catv.p2p.base.domain.UserInfo;
import com.catv.p2p.base.query.UserFileQueryObject;
import com.catv.p2p.base.service.IAccountService;
import com.catv.p2p.base.service.IRealAuthService;
import com.catv.p2p.base.service.IUserFileService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.util.BidConst;
import com.catv.p2p.base.util.UserContext;
import com.catv.p2p.business.domain.BidRequest;
import com.catv.p2p.business.query.BidRequestQueryObject;
import com.catv.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 */
@Controller
public class IndexController {

    @Autowired
    private IBidRequestService bidRequestService;

    @Autowired
    private IUserFileService userFileService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IRealAuthService realAuthService;

    @Autowired
    private IAccountService accountService;

    /**
     * 首页
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("bidRequests",bidRequestService.queryListForIndex());
        return "main";
    }

    /**
     * 前台查看借款详情
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
        if (UserContext.getCurrent() != null){
            if (UserContext.getCurrent().getId().equals(userinfo.getId())){
                model.addAttribute("self",true);
            } else {
                model.addAttribute("account",accountService.getByPrimaryKey(userinfo.getId()));
                model.addAttribute("self",false);
            }
        } else {
            model.addAttribute("self",false);
        }
        return "borrow_info";
    }

    /**
     * 投资页面
     * @return
     */
    @RequestMapping("/invest")
    public String invest(){
        return "invest";
    }

    /**
     * 投资页面列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("/invest_list")
    public String investList(BidRequestQueryObject qo,Model model){
        if (qo.getBidRequestState() == -1){
            qo.setBidRequestStates(new int[]{
                BidConst.BIDREQUEST_STATE_BIDDING
                        ,BidConst.BIDREQUEST_STATE_PAYING_BACK
                        ,BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
        }
        model.addAttribute("pageResult",bidRequestService.queryPageResult(qo));
        return "invest_list";
    }
}
