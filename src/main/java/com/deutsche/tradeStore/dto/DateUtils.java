package com.deutsche.tradeStore.dto;

import com.deutsche.tradeStore.exception.AppInternalException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /*get date without millsecound*/
    public static Date parseDate(Date date){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            throw new AppInternalException("Not a valid date");
        }
    }
}
