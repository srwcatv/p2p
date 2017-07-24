package com.catv.p2p.base.service;

/**
 * 发送验证码
 */
public interface IVerifyCodeService {

    /**
     *  手机验证码发送
     * @param phoneNumber 手机号
     */
    void sendVerifyCode(String phoneNumber);

    /**
     * 验证邮箱发送
     * @param email 邮箱
     */
    void sendEmail(String email);
}
