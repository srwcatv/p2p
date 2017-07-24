package com.catv.p2p.base.domain;

import com.catv.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

/**
 * 账户信息
 */
@Setter
@Getter
@Alias("Account")
public class Account extends BaseDomain {

    private int version;//对象版本信息
    private String tradePassword;//交易密码
    private BigDecimal usableAmount = BidConst.ZERO;//可用金额
    private BigDecimal freezeAmount = BidConst.ZERO;//冻结金额
    private BigDecimal unReceiveInterest = BidConst.ZERO;//待收利息
    private BigDecimal unReceivePrincipal = BidConst.ZERO;//待收本金
    private BigDecimal unReturnAmount = BidConst.ZERO;//待还金额
    private BigDecimal remainBorrowLimit = BidConst.INIT_BORROW_LIMIT;//剩余授信额度
    private BigDecimal borrowLimit = BidConst.INIT_BORROW_LIMIT;//授信额度

    public BigDecimal getTotalAmount(){
        return usableAmount.add(freezeAmount).add(unReceiveInterest).add(unReceivePrincipal);
    }
}
