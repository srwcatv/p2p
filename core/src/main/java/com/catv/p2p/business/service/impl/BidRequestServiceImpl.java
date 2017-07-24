package com.catv.p2p.business.service.impl;

import com.catv.p2p.base.domain.Account;
import com.catv.p2p.base.domain.UserInfo;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.service.IAccountService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.util.BidConst;
import com.catv.p2p.base.util.BitStatesUtils;
import com.catv.p2p.base.util.UserContext;
import com.catv.p2p.business.domain.BidRequest;
import com.catv.p2p.business.domain.BidRequestAuditHistory;
import com.catv.p2p.business.mapper.BidMapper;
import com.catv.p2p.business.mapper.BidRequestAuditHistoryMapper;
import com.catv.p2p.business.mapper.BidRequestMapper;
import com.catv.p2p.business.query.BidRequestQueryObject;
import com.catv.p2p.business.service.IBidRequestService;
import com.catv.p2p.business.util.CalculatetUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BidRequestServiceImpl implements IBidRequestService,
		ApplicationListener<ContextRefreshedEvent> {

	private List<BidRequest> reqQueue = new ArrayList<BidRequest>();

	/**
	 * 一个小时执行一次
	 */
	public void checkHourBidRequest() {
		// 检查下一个小时到期的标,并且状态处于招标中的;
		// 按照到期时间的先后放到reqQueue;
	}

	/**
	 * 每2秒钟执行一次
	 */
	public void checkBidRequestCancel() {
		// 检查在reqQueue的第一个标;如果时间未到,不管;如果时间到了,再去查询一次这个标的状态,如果仍然是招标中;执行流标
		// 如果处理了,移出队列
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 1,在系统启动的时候,查询有没有标时间到期,但是仍然处于招标中;
		// 如果有,执行流标
	}

	@Autowired
	private BidRequestMapper bidRequestMapper;

	@Autowired
	private BidMapper bidMapper;

	@Autowired
	private IUserInfoService userInfoService;

	@Autowired
	private IAccountService accountService;

	@Autowired
    private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;

	@Override
	public void update(BidRequest bidRequest) {
		int ret = bidRequestMapper.updateByPrimaryKey(bidRequest);
		if (ret == 0) {
			throw new RuntimeException("乐观锁失败   bidRequest:"
					+ bidRequest.getId());
		}
	}

	public BidRequest get(Long id) {
		return this.bidRequestMapper.selectByPrimaryKey(id);
	}

	public boolean canApplyBidRequest(Long logininfoId) {
		// 得到指定用户;
		// 判断1,基本资料;2,实名认证;3,视频认证;4风控资料分数;5没有借款在流程当中
		UserInfo userinfo = this.userInfoService.getByPrimaryKey(logininfoId);
		return userinfo != null && userinfo.getIsBasicInfo()
				&& userinfo.getIsRealAuth() && userinfo.getIsVedioAuth()
				&& userinfo.getScore() >= BidConst.BASE_BORROW_SCORE
				&& !userinfo.getHasBidRequestProcess();
	}

	@Override
	public void apply(BidRequest br) {
		Account account = this.accountService.getCurrent();
		// 首先满足最基本的申请条件;
		if (this.canApplyBidRequest(UserContext.getCurrent().getId())
				&& br.getBidRequestAmount().compareTo(
						BidConst.SMALLEST_BIDREQUEST_AMOUNT) >= 0// 系统最小借款金额<=借款金额
				&& br.getBidRequestAmount().compareTo(
						account.getRemainBorrowLimit()) <= 0// 借款金额<=剩余信用额度
				&& br.getCurrentRate()
						.compareTo(BidConst.SMALLEST_CURRENT_RATE) >= 0// 5<=利息
				&& br.getCurrentRate().compareTo(BidConst.MAX_CURRENT_RATE) <= 0// 利息<=20
				&& br.getMinBidAmount().compareTo(BidConst.SMALLEST_BID_AMOUNT) >= 0// 最小投标金额>=系统最小投标金额
		) {
			// ==========进入借款申请
			// 1,创建一个新的BidRequest,设置相关参数;
			BidRequest bidRequest = new BidRequest();
			bidRequest.setBidRequestAmount(br.getBidRequestAmount());
			bidRequest.setCurrentRate(br.getCurrentRate());
			bidRequest.setDescription(br.getDescription());
			bidRequest.setDisableDays(br.getDisableDays());
			bidRequest.setMinBidAmount(br.getMinBidAmount());
			bidRequest.setReturnType(br.getReturnType());
			bidRequest.setMonthes2Return(br.getMonthes2Return());
			bidRequest.setTitle(br.getTitle());
			// 2,设置相关值;
			bidRequest.setApplyTime(new Date());
			bidRequest
					.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
			bidRequest.setCreateUser(UserContext.getCurrent());
			bidRequest
					.setTotalRewardAmount(CalculatetUtil.calTotalInterest(
							bidRequest.getReturnType(),
							bidRequest.getBidRequestAmount(),
							bidRequest.getCurrentRate(),
							bidRequest.getMonthes2Return()));
			// 3,保存;
			this.bidRequestMapper.insert(bidRequest);
			// 4,给借款人添加一个状态码
			UserInfo userinfo = this.userInfoService.getCurrent();
			userinfo.setBitState(BitStatesUtils.addState(userinfo.getBitState(),BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS));
			this.userInfoService.update(userinfo);
		}
	}

    public PageResult queryPageResult(BidRequestQueryObject qo) {
        return new PageResult(bidRequestMapper.selectList(qo),bidRequestMapper.selectCount(qo),qo.getCurrentPage(),qo.getPageSize());
    }

    public void publishAudit(Long id, int state, String remark) {
	    //查出当前审核的对象
        BidRequest current = bidRequestMapper.selectByPrimaryKey(id);
        //判断当前审核对象不为空并且审核状态为待发布
        if (current != null && current.getBidRequestState() == BidConst.BIDREQUEST_STATE_PUBLISH_PENDING){
            //创建审核历史对象
            BidRequestAuditHistory history = new BidRequestAuditHistory();
            history.setAuditType(BidRequestAuditHistory.PUBLISH_AUDIT);
            history.setBidRequestId(current.getId());
            history.setApplier(current.getCreateUser());
            history.setApplyTime(current.getApplyTime());
            history.setAuditor(UserContext.getCurrent());
            history.setAuditTime(new Date());
            history.setRemark(remark);
            history.setState(state);
            history.setBidRequestId(current.getId());
            //保存审核历史对象
            bidRequestAuditHistoryMapper.insert(history);
            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                //审核通过，修改当前审核对象状态
                current.setBidRequestState(BidConst.BIDREQUEST_STATE_BIDDING);
                current.setNote(remark);
                current.setDisableDate(DateUtils.addDays(new Date(),current.getDisableDays()));
                current.setPublishTime(new Date());
            } else {
                //审核拒绝，修改当前审核对象状态
                current.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);
                //查出申请借款人对应的用户信息
                UserInfo applier = userInfoService.getByPrimaryKey(current.getCreateUser().getId());
                //移除用户信息的借款申请状态
                applier.setBitState(BitStatesUtils.removeState(applier.getBitState(),BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS));
                userInfoService.update(applier);
            }
            this.update(current);
        }
    }

    public List<BidRequestAuditHistory> listAuditHistoryByBidRequest(Long id) {
        return bidRequestAuditHistoryMapper.listByBidRequest(id);
    }

    public List<BidRequest> queryListForIndex() {
	    BidRequestQueryObject qo = new BidRequestQueryObject();
	    qo.setBidRequestStates(new int[]{BidConst.BIDREQUEST_STATE_BIDDING
                ,BidConst.BIDREQUEST_STATE_PAYING_BACK
                ,BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
	    qo.setPageSize(5);
	    qo.setOrderBy("bidRequestState");
	    qo.setOrderType("ASC");
        return bidRequestMapper.selectList(qo);
    }
}
