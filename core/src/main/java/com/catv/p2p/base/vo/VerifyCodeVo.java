package com.catv.p2p.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 验证码相关
 */
@Getter
@Setter
public class VerifyCodeVo {

    private String code;//验证码
    private String phoneNumber;//手机号
    private Date lastSendTime;//最后发送的时间
}
