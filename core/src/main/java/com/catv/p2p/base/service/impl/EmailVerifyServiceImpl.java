package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.domain.EmailVerify;
import com.catv.p2p.base.mapper.EmailVerifyMapper;
import com.catv.p2p.base.service.IEmailVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 邮箱验证信息操作
 */
@Service
public class EmailVerifyServiceImpl implements IEmailVerifyService {

    @Autowired
    EmailVerifyMapper emailVerifyMapper;

    public void save(EmailVerify emailVerify) {
        emailVerifyMapper.insert(emailVerify);
    }

    public EmailVerify getByUuid(String uuid) {
        return emailVerifyMapper.selectByUuid(uuid);
    }
}
