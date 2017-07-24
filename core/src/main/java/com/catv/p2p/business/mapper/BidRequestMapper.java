package com.catv.p2p.business.mapper;

import com.catv.p2p.business.domain.BidRequest;
import com.catv.p2p.business.query.BidRequestQueryObject;

import java.util.List;

public interface BidRequestMapper {

	int insert(BidRequest record);

	BidRequest selectByPrimaryKey(Long id);

	int updateByPrimaryKey(BidRequest record);

    List<BidRequest> selectList(BidRequestQueryObject qo);

    Integer selectCount(BidRequestQueryObject qo);
}