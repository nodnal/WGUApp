package com.example.wguapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String toString(Date d){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(d);
    }
}
