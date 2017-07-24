package com.catv.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 绑定邮箱验证的对象
 *
 */
@Setter
@Getter 
public class EmailVerify extends BaseDomain {

	private String uuid ;
	private String email ;
	private Long userInfo_id ;
	private Date sendTime ;
}
