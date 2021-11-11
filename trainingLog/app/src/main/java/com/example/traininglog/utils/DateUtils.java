package com.example.traininglog.utils;

import androidx.room.Database;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static long ToTimestamp(Date date) {
        return new Timestamp(date.getTime()).getTime();
    }

    public static Date FromTimestamp(long date) {
        return new Date(date);
    }

    public static Date lastMonday(Date today) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        while (cal.get(Calendar.DAY_OF_WEEK)!= Calendar.MONDAY){
            cal.add(Calendar.DATE, -1);
        }
        return cal.getTime();
    }


    public static Date nextMonday(Date today) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        while (cal.get(Calendar.DAY_OF_WEEK)!= Calendar.MONDAY){
            cal.add(Calendar.DATE, 1);
        }
        return cal.getTime();
    }
}
