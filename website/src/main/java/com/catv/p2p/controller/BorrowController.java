package com.catv.p2p.controller;

import com.catv.p2p.base.domain.LoginInfo;
import com.catv.p2p.base.service.IAccountService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.util.BidConst;
import com.catv.p2p.base.util.UserContext;
import com.catv.p2p.business.domain.BidRequest;
import com.catv.p2p.business.service.IBidRequestService;
import com.catv.p2p.util.RequiredLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 借款
 */
@Controller
public class BorrowController {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBidRequestService bidRequestService;

    /**
     * 借款页面
     * @param model
     * @return
     */
    @RequiredLogin
    @RequestMapping("/borrow")
    public String borrow(Model model){
        LoginInfo current = UserContext.getCurrent();
        if (current == null) {
            return "redirect:borrow.html";
        } else {
            model.addAttribute("userInfo",userInfoService.getByPrimaryKey(current.getId()));
            model.addAttribute("account",accountService.getByPrimaryKey(current.getId()));
            model.addAttribute("creditBorrowScore", BidConst.BASE_BORROW_SCORE);
            return "borrow";
        }
    }

    /**
     * 申请借款
     * @param model
     * @return
     */
    @RequiredLogin
    @RequestMapping("/borrowInfo")
    public String borrowInfo(Model model){
        boolean canApply = bidRequestService.canApplyBidRequest(UserContext.getCurrent().getId());
        if (canApply){
            model.addAttribute("minBidRequestAmount",BidConst.SMALLEST_BIDREQUEST_AMOUNT);
            model.addAttribute("account",accountService.getCurrent());
            model.addAttribute("minBidAmount", BidConst.SMALLEST_BID_AMOUNT);
            return "borrow_apply";
        } else {
            return "borrow_apply_result";
        }
    }

    /**
     * 借款申请提交
     * @param bidRequest
     * @return
     */
    @RequiredLogin
    @RequestMapping("/borrow_apply")
    public String borrowApply(BidRequest bidRequest){
        bidRequestService.apply(bidRequest);
        return "redirect:/borrowInfo.do";
    }
}
