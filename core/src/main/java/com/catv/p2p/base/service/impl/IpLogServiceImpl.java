package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.domain.IpLog;
import com.catv.p2p.base.mapper.IpLogMapper;
import com.catv.p2p.base.query.IpLogQueryObject;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.service.IIpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登陆日志服务
 */
@Service
public class IpLogServiceImpl implements IIpLogService {

    @Autowired
    private IpLogMapper ipLogMapper;

    public void save(IpLog ipLog) {
        ipLogMapper.insert(ipLog);
    }

    public PageResult queryPageResult(IpLogQueryObject qo) {
        int count = ipLogMapper.selectCount(qo);
        if (count == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(ipLogMapper.selectPageResultByQo(qo),ipLogMapper.selectCount(qo),qo.getCurrentPage(),qo.getPageSize());
    }
}
