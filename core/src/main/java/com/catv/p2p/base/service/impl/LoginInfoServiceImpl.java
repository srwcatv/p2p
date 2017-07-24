package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.domain.Account;
import com.catv.p2p.base.domain.IpLog;
import com.catv.p2p.base.domain.LoginInfo;
import com.catv.p2p.base.domain.UserInfo;
import com.catv.p2p.base.mapper.LoginInfoMapper;
import com.catv.p2p.base.service.IAccountService;
import com.catv.p2p.base.service.IIpLogService;
import com.catv.p2p.base.service.ILoginInfoService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.util.MD5;
import com.catv.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户登录注册
 */
@Service
public class LoginInfoServiceImpl implements ILoginInfoService {

    @Autowired
    private LoginInfoMapper logininfoMapper;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IIpLogService ipLogService;

    public void register(String username, String password) {
        int count = logininfoMapper.getCountByUsername(username);
        if (count > 0){
            throw new RuntimeException("改用户已存在");
        } else {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setPassword(MD5.encode(password));
            loginInfo.setUsername(username);
            loginInfo.setState(LoginInfo.STATE_NORMAL);
            loginInfo.setUserType(LoginInfo.USER_CLIENT);
            logininfoMapper.insert(loginInfo);
            Account account = new Account();
            account.setId(loginInfo.getId());
            account.setTradePassword(MD5.encode(password));
            accountService.add(account);
            UserInfo userInfo = new UserInfo();
            userInfo.setId(loginInfo.getId());
            userInfoService.add(userInfo);
        }
    }

    public boolean checkUsername(String username) {
        return !(logininfoMapper.getCountByUsername(username) > 0);
    }

    public LoginInfo login(String username, String password,int userType) {
        LoginInfo login = logininfoMapper.getByLogin(username, MD5.encode(password),userType);
        IpLog ipLog = new IpLog();
        ipLog.setIp(UserContext.getRemoteIp());
        ipLog.setUsername(username);
        ipLog.setLoginTime(new Date());
        ipLog.setLoginType(userType);
        if (login != null) {
            UserContext.putLoginInfo(login);
            ipLog.setLoginState(IpLog.SUCCESS_STATE);
        } else {
            ipLog.setLoginState(IpLog.FAILED_STATE);
        }
        ipLogService.save(ipLog);
        return login;
    }

    public void initAdmin() {
        int count = logininfoMapper.getByUserType(LoginInfo.USER_MANAGER);
        if (count == 0){
            LoginInfo admin = new LoginInfo();
            admin.setUsername("admin");
            admin.setPassword(MD5.encode("admin"));
            admin.setUserType(LoginInfo.USER_MANAGER);
            logininfoMapper.insert(admin);
        }
    }

    @Override
    public LoginInfo getById(Long id) {
        return logininfoMapper.selectByPrimaryKey(id);
    }
}
