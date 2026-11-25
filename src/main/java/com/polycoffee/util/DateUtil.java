package com.polycoffee.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Lop tien ich dinh dang va tinh toan ngay thang cho tang Servlet/DAO.
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
public class DateUtil {

    public static Date toDate(String text, String pattern) {
        if (text == null || text.isBlank()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            return sdf.parse(text.trim());
        } catch (Exception e) {
            return null;
        }
    }

    public static String toString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date != null ? date : new Date());
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }
}