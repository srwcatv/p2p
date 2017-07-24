package com.catv.p2p.base.query;

import com.catv.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class BaseAuditQueryObject extends QueryObject {

    private int state = -1;
    private Date beginTime;
    private Date endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setBeginTime(Date beginTime){
        this.beginTime = beginTime;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }

    public Date getBeginTime(){
        return this.beginTime == null?null: DateUtil.getBeginDate(beginTime);
    }

    public Date getEndTime(){
        return this.endTime == null?null:DateUtil.getEndDate(endTime);
    }
}
