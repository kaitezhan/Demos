package com.boneix.base.utils.date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间基础工具类
 *
 * @author boneix
 * @version [1.0, 2016年1月13日]
 */
public class DateUtils {

    private DateUtils() {
    }

    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

    public static final long MILLIS_PER_SECOND = 1000L;

    public static final long MILLIS_PER_MINUTE = 60000L;

    public static final long MILLIS_PER_HOUR = 3600000L;

    public static final long MILLIS_PER_DAY = 86400000L;

    public static Date addYears(Date date, int amount) {
        return add(date, 1, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, 2, amount);
    }

    public static Date addDays(Date date, int amount) {
        return add(date, 5, amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, 11, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, 12, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return add(date, 13, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return add(date, 14, amount);
    }

    public static Date add(Date date, int calendarFiled, int amount) {
        if (null == date) {
            LOG.error("DateUtils.add | The Date must not be null!");
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarFiled, amount);
        return c.getTime();
    }

    public static Date setYears(Date date, int amount) {
        return set(date, 1, amount);
    }

    public static Date setMonths(Date date, int amount) {
        return set(date, 2, amount);
    }

    public static Date setDays(Date date, int amount) {
        return set(date, 5, amount);
    }

    public static Date setHours(Date date, int amount) {
        return set(date, 11, amount);
    }

    public static Date setMinutes(Date date, int amount) {
        return set(date, 12, amount);
    }

    public static Date setSeconds(Date date, int amount) {
        return set(date, 13, amount);
    }

    public static Date setMilliSeconds(Date date, int amount) {
        return set(date, 14, amount);
    }

    public static Date set(Date date, int calendarFiled, int amount) {
        if (null == date) {
            LOG.error("DateUtils.set | The Date must not be null!");
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(calendarFiled, amount);
        return c.getTime();
    }

    public static Calendar toCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    public static String getDate() {
        return DateFormatUtils.formatDate(Calendar.getInstance().getTime());
    }

    public static String getDatetime() {
        return DateFormatUtils.formatDatetime(Calendar.getInstance().getTime());
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if ((null == date1) || (null == date2)) {
            LOG.error("DateUtils.isSameDay | The Date must not be null!");
            return false;
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if ((null == cal1) || (null == cal2)) {
            LOG.error("DateUtils.isSameDay | The Calendar must not be null!");
            return false;
        }

        return ((cal1.get(0) == cal2.get(0)) && (cal1.get(1) == cal2.get(1)) && (cal1.get(6) == cal2.get(6)));
    }

    public static Date addTwo(Date d1, Date d2) {
        if ((null == d1) || (null == d2)) {
            return null;
        }

        return new Date(d1.getTime() + d2.getTime());
    }

    public static Integer minusToYear(Date d1, Date d2) {
        if ((null == d1) || (null == d2)) {
            LOG.error("DateUtils.minusToYear | The Date must not be null!");
            return null;
        }

        Calendar cl1 = Calendar.getInstance();
        Calendar cl2 = Calendar.getInstance();
        cl1.setTime(d1);
        cl2.setTime(d2);
        return (cl1.get(1) - cl2.get(1));
    }

    public static Integer minusToMonth(Date d1, Date d2) {
        if ((null == d1) || (null == d2)) {
            LOG.error("DateUtils.minusToMonth | The Date must not be null!");
            return null;
        }

        Calendar cl1 = Calendar.getInstance();
        Calendar cl2 = Calendar.getInstance();
        cl1.setTime(d1);
        cl2.setTime(d2);
        return ((cl1.get(1) - cl2.get(1)) * 12 + cl1.get(2) - cl2.get(2));
    }

    public static int minusToDay(Date d1, Date d2) {
        return (int) (minusToMilliSecond(d1, d2) / 86400000L);
    }

    public static int minusToHours(Date d1, Date d2) {
        return (int) (minusToMilliSecond(d1, d2) / 3600000L);
    }

    public static long minusToMinutes(Date d1, Date d2) {
        return (minusToMilliSecond(d1, d2) / 60000L);
    }

    public static long minusToSeconds(Date d1, Date d2) {
        return (minusToMilliSecond(d1, d2) / 1000L);
    }

    public static Long minusToMilliSecond(Date d1, Date d2) {
        if ((null == d1) || (null == d2)) {
            LOG.error("DateUtils.minusToMilliSecond | The Date must not be null!");
            return null;
        }

        return (d1.getTime() - d2.getTime());
    }
}