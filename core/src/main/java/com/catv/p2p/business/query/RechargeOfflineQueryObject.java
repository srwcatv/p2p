package com.catv.p2p.business.query;

import com.catv.p2p.base.query.BaseAuditQueryObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 线下充值
 */
@Getter
@Setter
public class RechargeOfflineQueryObject extends BaseAuditQueryObject {

    private Long bankInfoId = -1L;
    private String tradeCode;

    public String getTradeCode(){
        return !StringUtils.hasLength(tradeCode)?null:this.tradeCode;
    }
}
