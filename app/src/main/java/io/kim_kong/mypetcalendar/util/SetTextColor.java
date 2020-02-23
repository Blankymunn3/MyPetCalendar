package io.kim_kong.mypetcalendar.util;


import android.icu.util.ChineseCalendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SetTextColor {

    private Boolean holiday;

    private int year, month, day;

    public SetTextColor() {
    }

    public SetTextColor(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int textColor() {

        String parseMonth, parseDay;
        parseMonth = String.format(Locale.getDefault(), "%02d", month);
        parseDay = String.format(Locale.getDefault(), "%02d", day);

        String inputDate = year + "" + parseMonth + parseDay;
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateFormat.parse(inputDate);
            assert date != null;
            calendar.setTime(date);
            holiday = checkHoliday(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || holiday) {
            return 0;
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return 1;
        } else {
            return 2;
        }
    }

    private boolean checkHoliday(Date date) {

        boolean result = false;

        String[] solar = {"0101", "0301", "0505", "0606", "0815", "1003" , "1009","1225"};
        String[] lunar = {"0101", "0102", "0408", "0814", "0815", "0816", "1230"};


        List<String> ListsolarLegalHoliday = Arrays.asList(solar);
        List<String> ListlunarLegalHoliday = Arrays.asList(lunar);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd", Locale.getDefault());

        String GregorianDate =  dateFormat.format(date);
        ChineseCalendar cc = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cc = new ChineseCalendar(date);
        }

        String m = null;
        String d = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            m = String.format("%02d", cc.get(ChineseCalendar.MONTH) + 1);
            d = String.format("%02d", cc.get(ChineseCalendar.DAY_OF_MONTH));
        }

        String lunarDate = m + d;

        if (ListsolarLegalHoliday.indexOf(GregorianDate) >= 0 ||
                ListlunarLegalHoliday.indexOf(lunarDate) >= 0 ) {
            result = true;
        }
        return result;
    }
}
