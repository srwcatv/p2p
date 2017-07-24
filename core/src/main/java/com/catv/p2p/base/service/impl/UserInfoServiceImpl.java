package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.domain.EmailVerify;
import com.catv.p2p.base.domain.UserInfo;
import com.catv.p2p.base.mapper.UserInfoMapper;
import com.catv.p2p.base.service.IEmailVerifyService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.util.BidConst;
import com.catv.p2p.base.util.BitStatesUtils;
import com.catv.p2p.base.util.DateUtil;
import com.catv.p2p.base.util.UserContext;
import com.catv.p2p.base.vo.VerifyCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户相关服务
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private IEmailVerifyService emailVerifyService;

    public UserInfo getCurrent(){
        return userInfoMapper.selectByPrimaryKey(UserContext.getCurrent().getId());
    }

    public int updateBasicInfo(UserInfo userInfo) {
        UserInfo old = this.getCurrent();
        old.setEducationBackground(userInfo.getEducationBackground());
        old.setHouseCondition(userInfo.getHouseCondition());
        old.setIncomeGrade(userInfo.getIncomeGrade());
        old.setKidCount(userInfo.getKidCount());
        old.setMarriage(userInfo.getMarriage());
        if (!old.getIsBasicInfo()){
            old.setBitState(BitStatesUtils.addState(old.getBitState(),BitStatesUtils.OP_BASIC_INFO));
        }
        return userInfoMapper.updateByPrimaryKey(old);
    }

    public List<Map<String, Object>> getList(String keyword) {
        return userInfoMapper.getList(keyword);
    }

    public void update(UserInfo userInfo) {
        int ret = userInfoMapper.updateByPrimaryKey(userInfo);
        if (ret == 0) {
            throw new RuntimeException("乐观锁失败,用户ID" + userInfo.getId());
        }
    }

    public int add(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    public UserInfo getByPrimaryKey(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    public void bindPhone(String phoneNumber, String code) {

        //获取存放在session中的verifyCodeVo
        VerifyCodeVo verifyCodeVo = UserContext.getVerifyCodeVo();
        //获取登陆账号的用户信息
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(UserContext.getCurrent().getId());
        //判断当前用户是否已经绑定手机号,没有绑定继续
        if (!userInfo.getIsBindPhone()) {
            if (verifyCodeVo != null) {
                //判断验证码是否过期或者正确
                if (DateUtil.getBetweenDate(new Date(), verifyCodeVo.getLastSendTime()) >= BidConst.VERIFYCODE_VAILDATE_SECOND
                        && !verifyCodeVo.getPhoneNumber().equals(phoneNumber)
                        && !verifyCodeVo.getCode().equalsIgnoreCase(code)) {
                    throw new RuntimeException("验证码不正确或已过期");
                } else {
                    //绑定手机号
                    userInfo.setBitState(BitStatesUtils.addState(userInfo.getBitState(), BitStatesUtils.OP_BIND_PHONE));
                    userInfo.setPhoneNumber(phoneNumber);
                    userInfoMapper.updateByPrimaryKey(userInfo);
                }
            }
        } else {
            throw new RuntimeException("该用户已经绑定手机号");
        }
    }

    public int getByPhoneNumber(String phoneNumber) {
        return userInfoMapper.selectByPhoneNumber(phoneNumber);
    }


    public int getByEmail(String email) {
        return userInfoMapper.selectByEmail(email);
    }

    public void bindEmail(String uuid) {
        //获取保存在数据库中的EmailVerify对象信息
        EmailVerify emailVerify = emailVerifyService.getByUuid(uuid);
        //判断是否存在
        if (emailVerify != null) {
            //获取要绑定邮箱的用户
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(emailVerify.getUserInfo_id());
            //判断用户是否已经绑定邮箱
            if (userInfo != null && !userInfo.getIsBindEmail()) {
                //判断连接是否在有效期内
                if (emailVerify != null && DateUtil.getBetweenDate(new Date(), emailVerify.getSendTime()) < 60 * 60 * 24 * 5) {
                    //添加邮箱绑定状态
                    userInfo.setBitState(BitStatesUtils.addState(userInfo.getBitState(), BitStatesUtils.OP_BIND_EMAIL));
                    //给用户设置邮箱
                    userInfo.setEmail(emailVerify.getEmail());
                    //更新用户
                    userInfoMapper.updateByPrimaryKey(userInfo);
                    //终止当前操作
                } else  {
                    throw new RuntimeException("该链接已经超过有效期");
                }
            } else {
                throw new RuntimeException("该用户已经绑定邮箱");
            }
        } else {
            throw new RuntimeException("无法查到该UUID对应的邮箱信息");
        }
    }
}
