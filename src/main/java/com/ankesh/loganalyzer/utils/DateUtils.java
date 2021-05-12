package com.ankesh.loganalyzer.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final Calendar calendar = Calendar.getInstance();
    public static final String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static int findMonth(String month) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM")
                .withLocale(Locale.ENGLISH);
        TemporalAccessor accessor = parser.parse(month);
        return accessor.get(ChronoField.MONTH_OF_YEAR);
    }
    public static Date buildDate(String month, String date, String year) {
        LocalDate localDate = LocalDate.of(Integer.parseInt(year), findMonth(month), Integer.parseInt(date));
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    public static String buildFormattedDate(Date loginDate) {
        calendar.setTime(loginDate);
        String month = monthName[calendar.get(calendar.MONTH)];
        int day = calendar.get(calendar.DAY_OF_MONTH);
        return day+month;
    }
}
