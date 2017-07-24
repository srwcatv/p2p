package com.catv.p2p.base.service;

import com.catv.p2p.base.domain.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 用户相关服务
 */
public interface IUserInfoService {

    void update(UserInfo userInfo);

    int add(UserInfo userInfo);

    UserInfo getByPrimaryKey(Long id);

    /**
     * 绑定手机号
     * @param phoneNumber 手机号
     * @param code 验证码
     *
     */
    void bindPhone(String phoneNumber, String code);

    /**
     * 根据手机号查找用户
     * @param phoneNumber
     * @return
     */
    int getByPhoneNumber(String phoneNumber);

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    int getByEmail(String email);

    /**
     * 确认绑定邮箱
     * @param uuid
     */
    void bindEmail(String uuid);

    /**
     * 当前登陆用户的信息
     * @return
     */
    UserInfo getCurrent();

    int updateBasicInfo(UserInfo userInfo);

    /**
     * 含有id,name的MAP
     * @param keyword
     * @return
     */
    List<Map<String,Object>> getList(String keyword);
}
