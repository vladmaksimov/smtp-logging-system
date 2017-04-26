package com.maksimov.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created on 02.03.17.
 */
public class Utils {

    private static final List<String> FORMATS = new ArrayList<String>() {{
        add("MMM  d HH:mm:ss");
        add("MMM dd HH:mm:ss");
        add("yyyy-MM-dd");
        add("MMM dd yyyy");
    }};

    private static final String SEARCH_OPERATOR = "%";

    private static Date parseDate(String dateToParse) {
        for (String format : FORMATS) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            try {
                return new java.sql.Date(formatter.parse(dateToParse).getTime());
            } catch (ParseException ignored) {
            }
        }
        return null;
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

    public static String getStringFromObject(Object o) {
        return o.toString();
    }

}
