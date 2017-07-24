package com.catv.p2p.business.mapper;

import com.catv.p2p.business.domain.Bid;
import java.util.List;

public interface BidMapper {

	int insert(Bid record);

	Bid selectByPrimaryKey(Long id);

	List<Bid> selectAll();

	List<Bid> selectByBidRequest(Long bidRequestId);
}