package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.domain.RealAuth;
import com.catv.p2p.base.domain.UserInfo;
import com.catv.p2p.base.mapper.RealAuthMapper;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.query.RealAuthQueryObject;
import com.catv.p2p.base.service.IRealAuthService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.util.BitStatesUtils;
import com.catv.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 实名认证相关业务
 */
@Service
public class RealAuthServiceImpl implements IRealAuthService {

    @Autowired
    private RealAuthMapper realAuthMapper;

    @Autowired
    private IUserInfoService userInfoService;

    public RealAuth query(Long id) {
        return realAuthMapper.selectByPrimaryKey(id);
    }

    public int apply(RealAuth realAuth) {
        UserInfo current = userInfoService.getCurrent();
        int ret = 0;
        //判断当前用户没有实名认证，并且申请状态不处于审核中
        if (!current.getIsRealAuth() && current.getRealAuthId() == null) {
            realAuth.setApplier(UserContext.getCurrent());
            realAuth.setApplyTime(new Date());
            realAuth.setState(RealAuth.STATE_NORMAL);
            ret = realAuthMapper.insert(realAuth);
            current.setRealAuthId(realAuth.getId());
            userInfoService.update(current);
        }
        return ret;
    }

    public PageResult queryPageResult(RealAuthQueryObject qo) {
        return new PageResult(realAuthMapper.queryList(qo), realAuthMapper.queryCount(qo), qo.getCurrentPage(), qo.getPageSize());
    }

    public int audit(RealAuth realAuth) {
        RealAuth oldRealAuth = realAuthMapper.selectByPrimaryKey(realAuth.getId());
        //判断是否存在该实名认证，并且状态是否正常
        if (oldRealAuth != null && oldRealAuth.getState() == RealAuth.STATE_NORMAL) {
            oldRealAuth.setState(RealAuth.STATE_AUDIT);
            oldRealAuth.setAuditor(UserContext.getCurrent());
            oldRealAuth.setAuditTime(new Date());
            oldRealAuth.setRemark(realAuth.getRemark());
            UserInfo applier = userInfoService.getByPrimaryKey(oldRealAuth.getApplier().getId());
            if (realAuth.getState() == RealAuth.STATE_AUDIT) {
                if (!applier.getIsRealAuth()) {
                    applier.setBitState(BitStatesUtils.addState(applier.getBitState(), BitStatesUtils.OP_REAL_AUTH));
                    applier.setRealAuthId(oldRealAuth.getId());
                    applier.setIdNumber(oldRealAuth.getIdNumber());
                    applier.setRealName(oldRealAuth.getRealName());
                }
            } else if (realAuth.getState() == RealAuth.STATE_REJECT) {
                if (!applier.getIsRealAuth() && applier.getRealAuthId() != null) {
                    applier.setRealAuthId(null);
                }
            }
            userInfoService.update(applier);
        }
        return realAuthMapper.updateByPrimaryKey(oldRealAuth);
    }
}
