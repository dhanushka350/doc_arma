package com.akvasoft.doctor_arma.common;

import java.util.Calendar;

public class DateTimeUtil {
    public static String getCurrentTimeStamp(){
        Calendar cal=Calendar.getInstance();
        return cal.get(Calendar.YEAR)+"_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DATE)+"__"+cal.get(Calendar.HOUR_OF_DAY)+"_"+cal.get(Calendar.MINUTE);
    }
}
