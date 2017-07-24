package com.catv.p2p.business.service;

import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.business.domain.BidRequest;
import com.catv.p2p.business.domain.BidRequestAuditHistory;
import com.catv.p2p.business.query.BidRequestQueryObject;

import java.util.List;

/**
 * 借款相关
 * 
 * @author Administrator
 * 
 */
public interface IBidRequestService {

	void update(BidRequest bidRequest);

	BidRequest get(Long id);

	/**
	 * 判断用户是否具有申请借款的权利
	 * 
	 * @return
	 */
	boolean canApplyBidRequest(Long logininfoId);

	/**
	 * 申请借款
	 * 
	 * @param bidRequest
	 */
	void apply(BidRequest bidRequest);

    /**
     * 申请借款分页
     * @param qo
     * @return
     */
    PageResult queryPageResult(BidRequestQueryObject qo);

    /**
     * 发表前审核提交
     * @param id
     * @param state
     * @param remark
     * @return
     */
    void publishAudit(Long id, int state, String remark);

    List<BidRequestAuditHistory> listAuditHistoryByBidRequest(Long id);

    /**
     * 首页显示的借款申请列表
     * @return
     */
    List<BidRequest> queryListForIndex();
}
