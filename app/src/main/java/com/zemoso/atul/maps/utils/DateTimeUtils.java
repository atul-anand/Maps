package com.zemoso.atul.maps.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class DateTimeUtils {
    private static final String TAG = DateTimeUtils.class.getSimpleName();

    public static String getDateFromCalendar(Calendar cal) {
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Date dat = cal.getTime();
        return df.format(dat);
    }

    public static String getDateFromElements(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return getDateFromCalendar(cal);
    }

    public static String getDateFromString(String date) {
        Log.d(TAG, date);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date dat;
        try {
            dat = df.parse(date);
            Log.d(TAG, String.valueOf(dat));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
            return df2.format(dat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTimeFromCalendar(Calendar cal) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df2 = new SimpleDateFormat("HH:mm Z");
        Date dat = cal.getTime();
        return df2.format(dat);
//        int hourStart = cal.get(Calendar.HOUR_OF_DAY);
//        int minuteStart = cal.get(Calendar.MINUTE);
//        if(minuteStart<10) {
//            if(hourStart>9)
//                return hourStart + ":0" + minuteStart;
//            return "0" + hourStart + ":0" + minuteStart;
//        }
//        if(hourStart<10)
//            return "0" + hourStart + ":" + minuteStart;
//        return hourStart + ":" + minuteStart;
    }

    public static String getTimeFromElements(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minute);
        return getTimeFromCalendar(cal);
    }

    public static String getTimeFromString(String time) {
        Log.d(TAG, time);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date dat;
        try {
            dat = df.parse(time);
            Log.d(TAG, String.valueOf(dat));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df2 = new SimpleDateFormat("hh:mm:ss a '('Z')'");
            return df2.format(dat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getTimeFromNow(String created_at) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date past;
        try {
            past = df.parse(created_at);

            Date now = new Date();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if (seconds < 60)
                return (seconds + " seconds ago");

            else if (minutes < 60)
                return (minutes + " minutes ago");

            else if (hours < 24)
                return (hours + " hours ago");

            else
                return (days + " days ago");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
