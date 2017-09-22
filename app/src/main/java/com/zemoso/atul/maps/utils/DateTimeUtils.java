package com.zemoso.atul.maps.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by zemoso on 18/9/17.
 */

public class DateTimeUtils {
    private static final String TAG = DateTimeUtils.class.getSimpleName();

    public static String getDateFromString(String date) {
        Log.d(TAG, date);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date dat = null;
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

    public static String getTimeFromString(String time) {
        Log.d(TAG, time);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date dat = null;
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
