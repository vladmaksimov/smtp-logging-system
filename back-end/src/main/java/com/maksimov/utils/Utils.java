package com.maksimov.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created on 02.03.17.
 */
public class Utils {

    private static final String DATE_FORMAT = "MMM  d HH:mm:ss";
    private static final String SEARCH_OPERATOR = "%";

    private static Date parseDate(String dateToParse) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date date = null;
        try {
            date = new java.sql.Date(formatter.parse(dateToParse).getTime());
        } catch (ParseException ignored) {
        }
        return date;
    }

    public static Date processDate(String dateToParse) {
        Date date = parseDate(dateToParse);
        if (date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            Integer currentYear = calendar.getWeekYear();
            calendar.setTime(date);
            calendar.set(Calendar.YEAR, currentYear);
            return calendar.getTime();
        }
    }

    public static String createSearchString(String search) {
        String result = null;
        if (search != null) {
            result = SEARCH_OPERATOR.concat(search).concat(SEARCH_OPERATOR);
        }
        return result;
    }

}
