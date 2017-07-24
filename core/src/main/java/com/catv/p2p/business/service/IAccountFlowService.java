package com.catv.p2p.business.service;

import com.catv.p2p.base.domain.Account;
import com.catv.p2p.business.domain.Bid;
import com.catv.p2p.business.domain.BidRequest;
import com.catv.p2p.business.domain.RechargeOffline;

import java.math.BigDecimal;

/**
 * 账户流水对象
 * 
 * @author Administrator
 * 
 */
public interface IAccountFlowService {

	/**
	 * 账户充值流水
	 * 
	 * @param r
	 * @param applierAccount
	 */
	void rechargeFlow(RechargeOffline r, Account applierAccount);

	/**
	 * 生成投标流水
	 * 
	 * @param bid
	 * @param currentAccount
	 */
	void bid(Bid bid, Account currentAccount);

	/**
	 * 满标审核拒绝退款流水
	 * 
	 * @param bid
	 * @param bidAccount
	 */
	void returnMoney(Bid bid, Account bidAccount);

	/**
	 * 生成成功收款流水
	 * 
	 * @param br
	 * @param borrowAccount
	 */
	void borrowSuccess(BidRequest br, Account borrowAccount);

	/**
	 * 缴纳借款手续费流水
	 * 
	 * @param manageChargeFee
	 * @param br
	 * @param borrowAccount
	 */
	void borrowChargeFee(BigDecimal manageChargeFee, BidRequest br,
                         Account borrowAccount);

	/**
	 * 生成成功投标流水
	 * 
	 * @param bid
	 * @param bidAccount
	 */
	void bidSuccess(Bid bid, Account bidAccount);

//	/***
//	 * 生成用户提现申请流水
//	 *
//	 * @param m
//	 * @param account
//	 */
//	void moneyWithDrawApply(MoneyWithdraw m, Account account);
//
//	/**
//	 * 生成提现申请拒绝流水
//	 *
//	 * @param m
//	 * @param account
//	 */
//	void withDrawFailed(MoneyWithdraw m, Account account);
//
//	/**
//	 * 提现手续费流水
//	 *
//	 * @param m
//	 * @param account
//	 */
//	void withDrawChargeFee(MoneyWithdraw m, Account account);
//
//	/**
//	 * 提现成功流水
//	 *
//	 * @param m
//	 * @param account
//	 */
//	void withDrawSuccess(BigDecimal amount, Account account);
//
//	/**
//	 * 执行还款操作流水
//	 *
//	 * @param ps
//	 * @param borrowAccount
//	 */
//	void doReturnMoney(PaymentSchedule ps, Account borrowAccount);
//
//	/**
//	 * 投资人回款流水
//	 *
//	 * @param psd
//	 * @param bidAccount
//	 */
//	void receiveMoney(PaymentScheduleDetail psd, Account bidAccount);
//
//	/**
//	 * 生成支付利息管理费流水
//	 *
//	 * @param psd
//	 * @param interestChargeFee
//	 * @param bidAccount
//	 */
//	void interestChargeFee(PaymentScheduleDetail psd,
//                           BigDecimal interestChargeFee, Account bidAccount);

}
