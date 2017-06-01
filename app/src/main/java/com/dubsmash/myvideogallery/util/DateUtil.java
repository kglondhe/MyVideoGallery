package com.dubsmash.myvideogallery.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by kapillondhe on 01/06/17.
 */

public class DateUtil {

    public static String convertToHHMMSS(long duration){

        final long hours = TimeUnit.MILLISECONDS.toHours(duration);
        duration -= TimeUnit.HOURS.toMillis(hours);
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        duration -= TimeUnit.MINUTES.toMillis(minutes);
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);

        return hours == 0 ? String.format((minutes < 10 ? "0%d:" : "%d:") + (seconds < 10 ? "0%d" : "%d"), minutes, seconds) : String.format((hours < 10 ? "0%d:" : "%d:") + (minutes < 10 ? "0%d:" : "%d:") + (seconds < 10 ? "0%d" : "%d"), hours, minutes, seconds);

    }

    public static String convertToDate(long time){
        final Date date = new Date(time);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        return simpleDateFormat.format(date);
    }

}
