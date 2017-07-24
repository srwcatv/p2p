package com.catv.p2p.business.service;

import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.business.domain.RechargeOffline;
import com.catv.p2p.business.query.RechargeOfflineQueryObject;

/**
 * 线下充值
 */
public interface IRechargeOfflineService {

    /**
     * 保存充值记录
     * @param ro
     * @return
     */
    int save(RechargeOffline ro);

    PageResult queryPageResult(RechargeOfflineQueryObject qo);

    /**
     * 线下充值审核
     * @param id
     * @param remark
     * @param state
     */
    void audit(Long id, String remark, int state);
}
