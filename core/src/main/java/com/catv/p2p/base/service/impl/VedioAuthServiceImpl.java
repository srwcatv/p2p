package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.domain.UserInfo;
import com.catv.p2p.base.domain.VedioAuth;
import com.catv.p2p.base.mapper.VedioAuthMapper;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.query.VedioAuthQueryObject;
import com.catv.p2p.base.service.ILoginInfoService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.service.IVedioAuthService;
import com.catv.p2p.base.util.BitStatesUtils;
import com.catv.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 实名认证相关业务
 */
@Service
public class VedioAuthServiceImpl implements IVedioAuthService {

    @Autowired
    private VedioAuthMapper vedioAuthMapper;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ILoginInfoService loginInfoService;

    public VedioAuth query(Long id) {
        return vedioAuthMapper.selectByPrimaryKey(id);
    }

    public PageResult queryPageResult(VedioAuthQueryObject qo) {
        return new PageResult(vedioAuthMapper.queryList(qo), vedioAuthMapper.queryCount(qo), qo.getCurrentPage(), qo.getPageSize());
    }

    public void audit(Long id,String remark,int state) {
        //查询要审核的用户对象
        UserInfo userInfo = userInfoService.getByPrimaryKey(id);
        //判断该对象是否存在并且没有通过视频认证
        if (userInfo != null && !userInfo.getIsVedioAuth()){
            VedioAuth vedioAuth = new VedioAuth();
            vedioAuth.setAuditor(UserContext.getCurrent());
            vedioAuth.setAuditTime(new Date());
            vedioAuth.setRemark(remark);
            vedioAuth.setState(state);
            vedioAuth.setApplier(loginInfoService.getById(userInfo.getId()));
            vedioAuth.setApplyTime(new Date());
            //判断审核的状态是否通过
            if (state == VedioAuth.STATE_AUDIT){
                userInfo.setBitState(BitStatesUtils.addState(userInfo.getBitState(), BitStatesUtils.OP_VEDIO_AUTH));
                userInfoService.update(userInfo);
            }
            vedioAuthMapper.insert(vedioAuth);
        } else {
            throw new RuntimeException("该用户不存在");

        }
    }
}
