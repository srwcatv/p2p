package com.catv.p2p.business.service.impl;

import com.catv.p2p.base.domain.Account;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.service.IAccountService;
import com.catv.p2p.base.util.UserContext;
import com.catv.p2p.business.domain.RechargeOffline;
import com.catv.p2p.business.mapper.RechargeOfflineMapper;
import com.catv.p2p.business.query.RechargeOfflineQueryObject;
import com.catv.p2p.business.service.IAccountFlowService;
import com.catv.p2p.business.service.IPlatformBankInfoService;
import com.catv.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 线下充值
 */
@Service
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {

    @Autowired
    private RechargeOfflineMapper rechargeOfflineMapper;

    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountFlowService accountFlowService;

    public int save(RechargeOffline ro) {
        if (ro != null){
            RechargeOffline newRo = new RechargeOffline();
            newRo.setTradeCode(ro.getTradeCode());
            newRo.setTradeTime(ro.getTradeTime());
            newRo.setAmount(ro.getAmount());
            newRo.setNote(ro.getNote());
            newRo.setApplier(UserContext.getCurrent());
            newRo.setApplyTime(new Date());
            newRo.setBankInfo(platformBankInfoService.get(ro.getBankInfo().getId()));
            newRo.setState(RechargeOffline.STATE_NORMAL);
            return rechargeOfflineMapper.insert(newRo);
        }
        return 0;
    }

    public PageResult queryPageResult(RechargeOfflineQueryObject qo) {
        return new PageResult(
                rechargeOfflineMapper.queryList(qo)
                ,rechargeOfflineMapper.queryCount(qo)
                ,qo.getCurrentPage()
                ,qo.getPageSize()
        );
    }

    public void audit(Long id, String remark, int state) {
        // 查询线下充值对象;设置相关属性;
        RechargeOffline r = this.rechargeOfflineMapper.selectByPrimaryKey(id);
        if (r != null && r.getState() == RechargeOffline.STATE_NORMAL) {
            // 审核通过
            r.setAuditor(UserContext.getCurrent());
            r.setAuditTime(new Date());
            r.setRemark(remark);
            r.setState(state);
            if (state == RechargeOffline.STATE_AUDIT) {
                // **1,得到申请人的账户对象;
                Account applierAccount = this.accountService.getByPrimaryKey(r.getApplier()
                        .getId());
                // **2,增加账户的可用余额;
                applierAccount.setUsableAmount(applierAccount.getUsableAmount()
                        .add(r.getAmount()));
                // **3,生成一条充值流水
                this.accountFlowService.rechargeFlow(r, applierAccount);
                this.accountService.update(applierAccount);
            }
            this.rechargeOfflineMapper.updateByPrimaryKey(r);
        }
    }
}
