package com.yevhent.springbootdemo.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-dd-MM");

    public static Date convert(String text) {
        try {
            return DATE_FORMAT.parse(text);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String dateTimeNow() {
        return new Date().toString();
    }
}