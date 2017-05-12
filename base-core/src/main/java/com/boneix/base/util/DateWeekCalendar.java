package com.boneix.base.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by rzhang on 2017/4/28.
 */
public class DateWeekCalendar {

    private Date date;

    public DateWeekCalendar() {
        this.date = new Date();
    }

    public DateWeekCalendar(Date date) {
        this.date = date;
    }

    /**
     * 获取当前时间所在年份
     *
     * @return
     */
    public int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间所在月份
     *
     * @return
     */
    public Date getMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取当前时间所在第几周
     *
     * @return
     */
    public int getWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前时间所在周的开始日期
     *
     * @return
     */
    public Date getFirstDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 获取当前时间所在周的结束日期
     *
     * @return
     */
    public Date getLastDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

}
