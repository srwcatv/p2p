package com.catv.p2p.base.service;

import com.catv.p2p.base.domain.EmailVerify;

/**
 * 邮箱验证信息
 */
public interface IEmailVerifyService {

    void save(EmailVerify emailVerify);

    EmailVerify getByUuid(String uuid);
}
