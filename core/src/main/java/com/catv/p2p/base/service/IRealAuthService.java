package com.catv.p2p.base.service;

import com.catv.p2p.base.domain.RealAuth;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.query.RealAuthQueryObject;

/**
 * 实名认证业务相关
 */
public interface IRealAuthService {
    RealAuth query(Long id);

    /**
     * 实名认证申请
     * @param realAuth
     * @return
     */
    int apply(RealAuth realAuth);

    /**
     * 实名认证分页查询
     * @param qo
     * @return
     */
    PageResult queryPageResult(RealAuthQueryObject qo);

    /**
     * 实名认证审核
     * @param realAuth
     * @return
     */
    int audit(RealAuth realAuth);
}



