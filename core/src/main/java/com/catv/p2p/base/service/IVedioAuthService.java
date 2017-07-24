package com.catv.p2p.base.service;

import com.catv.p2p.base.domain.VedioAuth;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.query.VedioAuthQueryObject;

/**
 * 视频认证业务相关
 */
public interface IVedioAuthService {
    VedioAuth query(Long id);

    /**
     * 视频认证分页查询
     * @param qo
     * @return
     */
    PageResult queryPageResult(VedioAuthQueryObject qo);

    /**
     * 视频认证审核
     * @param id
     * @param remark
     * @return
     */
    void audit(Long id,String remark,int state);
}



