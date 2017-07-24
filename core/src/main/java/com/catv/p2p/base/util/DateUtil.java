package com.catv.p2p.base.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理
 */
public class DateUtil {


    /**
     * 开始时间
     * @param date
     * @return
     */
    public static Date getBeginDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        date = calendar.getTime();
        return date;
    }

    /**
     * 结束时间
     *
     * @param
     * @return
     */
    public static Date getEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.add(Calendar.SECOND, -1);

        date = calendar.getTime();
        return date;
    }

    /**
     * 获得两个时间的差
     *
     * @param nowDate      现在的时间
     * @param lastSendDate 最后一次发送的时间
     * @return 两个时间差
     */
    public static Long getBetweenDate(Date nowDate, Date lastSendDate) {
        return Math.abs(nowDate.getTime() - lastSendDate.getTime()) / 1000;
    }
}
