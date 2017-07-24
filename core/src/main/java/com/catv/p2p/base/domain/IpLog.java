package com.catv.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 登陆日志
 */
@Setter
@Getter
@Alias("IpLog")
public class IpLog {

    public static final int SUCCESS_STATE = 1;
    public static final int  FAILED_STATE = 0;

    private Long id;

    private String ip;

    private int loginState;

    private String username;

    private int loginType;

    private Date loginTime;

    public String getStateDisplay() {
        return loginState == SUCCESS_STATE?"登陆成功":"登陆失败";
    }

    public String getLoginTypeDisplay() {
        return loginType == LoginInfo.USER_CLIENT?"前台用户":"后台用户";
    }
}