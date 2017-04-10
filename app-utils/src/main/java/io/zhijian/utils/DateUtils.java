package io.zhijian.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author Hao
 * @create 2017-04-10
 */
public class DateUtils {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD = "MM-dd";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String HH_MM = "HH:mm";

    public final static DateTimeFormatter FORMATTER_YYYY_MM_DD_HH_MM_SS = DateTimeFormat.forPattern(YYYY_MM_DD_HH_MM_SS);
    public final static DateTimeFormatter FORMATTER_YYYY_MM_DD_HH_MM = DateTimeFormat.forPattern(YYYY_MM_DD_HH_MM);
    public final static DateTimeFormatter FORMATTER_YYYY_MM_DD = DateTimeFormat.forPattern(YYYY_MM_DD);
    public final static DateTimeFormatter FORMATTER_MM_DD = DateTimeFormat.forPattern(MM_DD);
    public final static DateTimeFormatter FORMATTER_HH_MM_SS = DateTimeFormat.forPattern(HH_MM_SS);
    public final static DateTimeFormatter FORMATTER_HH_MM = DateTimeFormat.forPattern(HH_MM);

    public static String format(String dateTime, String targetPattern) {
        return format(dateTime, FORMATTER_YYYY_MM_DD_HH_MM_SS, targetPattern);
    }

    public static String format(String dateTime, DateTimeFormatter formatter, String targetPattern) {
        DateTime result = DateTime.parse(dateTime, formatter);
        return result.toString(targetPattern);
    }

    public static String format(Date time, String targetPattern) {
        DateTime dateTime = new DateTime(time);
        return dateTime.toString(targetPattern);
    }

    public static Date format(String dateTime, DateTimeFormatter formatter) {
        DateTime time = DateTime.parse(dateTime, formatter);
        return time.toDate();
    }

    public static Date format(String dateTime) {
        return format(dateTime, FORMATTER_YYYY_MM_DD_HH_MM_SS);
    }

    public static String toYmd(Date dateTime) {
        return format(dateTime, YYYY_MM_DD);
    }

    public static String toMd(Date dateTime) {
        return format(dateTime, MM_DD);
    }

    public static String toHm(Date dateTime) {
        return format(dateTime, HH_MM);
    }

    public static String toYmdHm(Date dateTime) {
        return format(dateTime, YYYY_MM_DD_HH_MM);
    }

    public static Date startOfDay(Date value) {
        DateTime dateTime = new DateTime(value);
        dateTime = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 00, 00, 00);
        return dateTime.toDate();
    }

    public static Date endOfDay(Date value) {
        DateTime dateTime = new DateTime(value);
        dateTime = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 23, 59, 59);
        return dateTime.toDate();
    }

    public static void main(String[] args) {
        String s = format("2017-02-03 12:23:23", HH_MM_SS);
        Date d = format("2017-02-03", FORMATTER_YYYY_MM_DD);
        String s1 = format(d, HH_MM_SS);
        System.out.println(s1);

        String date = toHm(new Date());
        System.out.println(date);
    }
}
