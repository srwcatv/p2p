package com.catv.p2p.base.domain;

import com.catv.p2p.base.util.BitStatesUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 用户信息
 */
@Setter
@Getter
@Alias("UserInfo")
public class UserInfo extends BaseDomain {

    private int version;//版本号
    private Long bitState = 0L;//用户状态值
    private String realName;//实名（冗余数据）
    private String idNumber;//身份证号（冗余数据）
    private String phoneNumber;//电话
    private String email;//邮箱
    private int score;//风控分数
    private Long realAuthId;//该用户对应的实名认证对象
    private SystemDictionaryItem incomeGrade;//收入
    private SystemDictionaryItem marriage;//婚姻
    private SystemDictionaryItem kidCount;//子女
    private SystemDictionaryItem educationBackground;//学历
    private SystemDictionaryItem houseCondition;//住房条件

    /**
     * 返回用户是否已经绑定手机
     *
     * @return
     */
    public boolean getIsBindPhone() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_BIND_PHONE);
    }

    /**
     * 返回用户是否已经绑定邮箱
     *
     * @return
     */
    public boolean getIsBindEmail() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_BIND_EMAIL);
    }

    /**
     * 返回用户是否已经填写了基本资料
     *
     * @return
     */
    public boolean getIsBasicInfo() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_BASIC_INFO);
    }

    /**
     * 返回用户是否已经实名认证
     *
     * @return
     */
    public boolean getIsRealAuth() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_REAL_AUTH);
    }

    /**
     * 返回用户是否视频认证
     *
     * @return
     */
    public boolean getIsVedioAuth() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_VEDIO_AUTH);
    }

    /**
     * 返回用户是否绑定银行卡
     *
     * @return
     */
    public boolean getIsBindBank() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_BIND_BANKINFO);
    }

    /**
     * 返回用户是否有一个借款在处理流程当中
     *
     * @return
     */
    public boolean getHasBidRequestProcess() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
    }

    /**
     * 返回用户是否有一个提现申请在处理流程当中
     *
     * @return
     */
    public boolean getHasWithdrawProcess() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_HAS_MONEYWITHDRAW_PROCESS);
    }
}
