package com.rkarp.appcore.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CommonUtil {

    /**
     * Returns the date in a given format
     * Example: dd.MM.yy
     */
    public static String getDate(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date dateToday = Calendar.getInstance().getTime(); //Get date using calendar object.
        return dateFormat.format(dateToday); //returns date string
    }

    /**
     * Returns the time in a given format
     * Example: HH-mm-ss
     */
    public static String getTime(String format) {
        DateFormat timeFormat = new SimpleDateFormat(format);
        Date currentTime = Calendar.getInstance().getTime(); //Get time using calendar object.
        return timeFormat.format(currentTime); //returns time string
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
