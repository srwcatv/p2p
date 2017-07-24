package com.catv.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 视频认证对象
 * @author Administrator
 * 
 */
@Setter
@Getter
@Alias("VedioAuth")
public class VedioAuth extends BaseAuditDomain {

}
