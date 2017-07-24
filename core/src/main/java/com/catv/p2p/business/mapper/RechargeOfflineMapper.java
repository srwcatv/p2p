package com.catv.p2p.business.mapper;

import com.catv.p2p.business.domain.RechargeOffline;
import com.catv.p2p.business.query.RechargeOfflineQueryObject;

import java.util.List;

public interface RechargeOfflineMapper {

    int insert(RechargeOffline record);

    RechargeOffline selectByPrimaryKey(Long id);

    List<RechargeOffline> selectAll();

    int updateByPrimaryKey(RechargeOffline record);

    List queryList(RechargeOfflineQueryObject qo);

    Integer queryCount(RechargeOfflineQueryObject qo);
}