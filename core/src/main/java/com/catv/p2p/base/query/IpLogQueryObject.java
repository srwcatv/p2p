package com.catv.p2p.base.query;

import com.catv.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 日志高级查询
 */
@Getter
@Setter
public class IpLogQueryObject extends QueryObject {

    private int state = -1;
    private Date beginDate;
    private Date endDate;
    private String username;
    private int loginType = -1;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getBeginDate() {
        return beginDate != null?DateUtil.getBeginDate(this.beginDate):null;
    }

    public Date getEndDate() {
        return endDate != null?DateUtil.getEndDate(this.endDate):null;
    }

    public String getUsername() {
        return this.username == null?null:username;
    }
}
