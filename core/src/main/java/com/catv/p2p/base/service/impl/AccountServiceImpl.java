package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.domain.Account;
import com.catv.p2p.base.mapper.AccountMapper;
import com.catv.p2p.base.service.IAccountService;
import com.catv.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户相关服务
 */
@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountMapper accountMapper;

    public void update(Account account) {
        int ret = accountMapper.updateByPrimaryKey(account);
        if (ret == 0) {
            throw new RuntimeException("乐观锁失败,账户ID" + account.getId());
        }
    }

    public void add(Account account) {
        accountMapper.insert(account);
    }

    public Account getByPrimaryKey(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    public Account getCurrent() {
        return this.getByPrimaryKey(UserContext.getCurrent().getId());
    }
}
