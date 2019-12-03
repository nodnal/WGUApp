package com.example.wguapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String toString(Date d){
        if (d == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(d);
    }
}
