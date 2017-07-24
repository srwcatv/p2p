package com.catv.p2p.base.service;

import com.catv.p2p.base.domain.Account;

/**
 * 账户相关服务
 */
public interface IAccountService {
    void update(Account account);

    void add(Account account);

    Account getByPrimaryKey(Long id);

    Account getCurrent();
}
