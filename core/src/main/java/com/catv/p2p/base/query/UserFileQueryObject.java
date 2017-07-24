package com.catv.p2p.base.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 实名认证高级查询
 */
@Setter
@Getter
public class UserFileQueryObject extends BaseAuditQueryObject {

    private Long applierId;
}
