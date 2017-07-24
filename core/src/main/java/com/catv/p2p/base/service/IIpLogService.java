package com.catv.p2p.base.service;

import com.catv.p2p.base.domain.IpLog;
import com.catv.p2p.base.query.IpLogQueryObject;
import com.catv.p2p.base.query.PageResult;

/**
 * 登陆日志服务
 */
public interface IIpLogService {

    void save(IpLog ipLog);

    PageResult queryPageResult(IpLogQueryObject qo);
}
