package com.paysky.momogrow.utilis;

import com.paysky.momogrow.helper.NoProguard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil extends NoProguard {

    public static String getDateTimeLocalTrxn() {

        int arg1 = Calendar.getInstance().get(Calendar.YEAR);
        String arg1s = "" + arg1;
        int random = (int) (Math.random() * 50 + 1);

        arg1s = arg1s.substring(2, 4);
        int arg2 = Calendar.getInstance().get(Calendar.MONTH);
        int arg3 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int arg4 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int arg5 = Calendar.getInstance().get(Calendar.MINUTE);
        int arg6 = Calendar.getInstance().get(Calendar.SECOND);
        int arg7 = Calendar.getInstance().get(Calendar.MILLISECOND);

        arg2 = arg2 + 1;


        return "" + new StringBuilder().append(arg1s).append("")
                .append((arg2 < 10 ? "0" + arg2 : arg2)).append("").append((arg3 < 10 ? "0" + arg3 : arg3))
                .append((arg4 < 10 ? "0" + arg4 : arg4)).append((arg5 < 10 ? "0" + arg5 : arg5))
                .append((arg6 < 10 ? "0" + arg6 : arg6)).append("").append(arg7) + random;
    }

    public static String getDateTimeExpirePayLinkPlusOneDay() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        int arg1 = cal.get(Calendar.YEAR);
        int arg2 = cal.get(Calendar.MONTH) + 1;
        int arg3 = cal.get(Calendar.DAY_OF_MONTH);
        int arg4 = cal.get(Calendar.HOUR_OF_DAY);
        int arg5 = cal.get(Calendar.MINUTE);

        return "" + new StringBuilder().append(arg1).append("")
                .append((arg2 < 10 ? "0" + arg2 : arg2)).append("").append((arg3 < 10 ? "0" + arg3 : arg3))
                .append((arg4 < 10 ? "0" + arg4 : arg4)).append((arg5 < 10 ? "0" + arg5 : arg5));
    }



}
