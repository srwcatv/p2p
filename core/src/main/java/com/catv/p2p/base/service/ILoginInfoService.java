package com.catv.p2p.base.service;

import com.catv.p2p.base.domain.LoginInfo;

/**
 *  登陆注册信息
 */
public interface ILoginInfoService {

    /**
     *  用户注册
     * @param username 用户名
     * @param password 密码
     */
    void register(String username,String password);

    /**
     * 检查是否有重复的用户
     * @param username
     * @return
     */
    boolean checkUsername(String username);

    LoginInfo login(String username, String password,int userType);

    /**
     * 初始化超级管理员
     */
    void initAdmin();

    LoginInfo getById(Long id);
}
