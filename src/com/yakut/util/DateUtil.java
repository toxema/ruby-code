package com.yakut.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yakut
 */
public class DateUtil {

    static SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    static SimpleDateFormat formatFull = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static void uyu(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void uyu() {
        uyu(500);
    }

    public static void sleep(int time) {
        uyu(time);
    }

    public static String formatTime(Date date) {
        String s = null;
        try {
            s = timeFormat.format(date);
        } catch (Exception ex) {
            s = "HATA";
        }
        return s;
    }

    public static Date fullParseDate(String text) {
        Date date = null;
        try {
            date = formatFull.parse(text);
        } catch (Exception ex) {
        }
        return date;
    }

    public static String fullFormatDate(Date date) {
        if (date == null) {
            return "";
        }

        return formatFull.format(date);
    }

    public static String formatDate(Date date) {
        String s;
        try {
            s = format.format(date);
        } catch (Exception ex) {
            s = "HATA";
        }
        return s;
    }

    public static Date parseDate(String d) {
        Date date = null;
        try {
            date = format.parse(d);
        } catch (Exception ex) {
        }
        return date;
    }

    public static Date parseTime(String time) {
        Date date = null;
        try {
            date = timeFormat.parse(time);
        } catch (Exception ex) {
        }
        return date;
    }

    public static String getDate() {
        return formatDate(new Date());
    }

    public static String getTime() {
        return formatTime(new Date());
    }

    public static String getTimeStamp() {
        return fullFormatDate(new Date());
    }

    public static Date getGunBaslangis() {
        Date bas = fullParseDate(formatDate(new Date()) + " 00:00:00");
        return bas;
    }

    public static Date getGunBitis() {
        Date bas = fullParseDate(formatDate(new Date()) + " 23:59:59");
        return bas;
    }
}
