package com.catv.p2p.business.query;

import com.catv.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 申请借款高级查询
 */
@Setter
@Getter
public class BidRequestQueryObject extends QueryObject {
    private int bidRequestState = -1;
    private int[] bidRequestStates;
    private String orderType;
    private String orderBy;
}
