package com.polycoffee.util;

import jakarta.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Lop tien ich doc va ep kieu tham so tu HttpServletRequest (Lab 4 - Bai 1).
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
public class ParamUtil {

    public static String getString(HttpServletRequest request, String name) {
        try {
            String value = request.getParameter(name);
            return value != null ? value.trim() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public static int getInt(HttpServletRequest request, String name) {
        try {
            String value = request.getParameter(name);
            return value != null && !value.isBlank() ? Integer.parseInt(value.trim()) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getLong(HttpServletRequest request, String name) {
        try {
            String value = request.getParameter(name);
            return value != null && !value.isBlank() ? Long.parseLong(value.trim()) : 0L;
        } catch (Exception e) {
            return 0L;
        }
    }

    public static boolean getBoolean(HttpServletRequest request, String name) {
        try {
            String value = request.getParameter(name);
            if (value == null) {
                return false;
            }
            String v = value.trim().toLowerCase();
            return "true".equals(v) || "1".equals(v) || "on".equals(v) || "yes".equals(v);
        } catch (Exception e) {
            return false;
        }
    }

    public static Date getDate(HttpServletRequest request, String name, String pattern) {
        try {
            String value = request.getParameter(name);
            if (value == null || value.isBlank()) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            return sdf.parse(value.trim());
        } catch (Exception e) {
            return null;
        }
    }
}
