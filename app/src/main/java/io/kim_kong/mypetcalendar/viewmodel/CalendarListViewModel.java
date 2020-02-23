package io.kim_kong.mypetcalendar.viewmodel;


import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import io.kim_kong.mypetcalendar.livedata.CalendarLiveData;
import io.kim_kong.mypetcalendar.model.Winner;
import io.kim_kong.mypetcalendar.util.CalendarDateFormat;
import io.kim_kong.mypetcalendar.util.EmptyKey;

public class CalendarListViewModel extends ViewModel {

    public CalendarLiveData<String> mTitleMonth = new CalendarLiveData<>();
    public CalendarLiveData<String> mTitleYear = new CalendarLiveData<>();
    public CalendarLiveData<ArrayList<Object>> mCalendarList = new CalendarLiveData<>(new ArrayList<>());

    public int mCenterPosition;

    private Date[] origin;

    public void initCalendarList(ArrayList<Winner> image, GregorianCalendar cal) {
        setCalendarList(cal, image);
    }

    public void setTitle(int position) {
        try {
            Object item = mCalendarList.getValue().get(position);
            if (item instanceof Long) {
                setTitle((Long) item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setTitle(long time) {
        mTitleMonth.setValue(CalendarDateFormat.CalendarDateFormat.getDate(time, CalendarDateFormat.CALENDAR_HAEDER_MONTH_FORMAT));
        mTitleYear.setValue(CalendarDateFormat.CalendarDateFormat.getDate(time, CalendarDateFormat.CALENDAR_HEADER_YEAR_FORMAT));
    }

    private void setCalendarList(GregorianCalendar cal, ArrayList<Winner> winners) {
        String strYear, strMonth, strDay;

        setTitle(cal.getTimeInMillis());
        SimpleDateFormat originFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());

        origin = new Date[winners.size()];

        ArrayList<Object> calendarList = new ArrayList<>();

        try {
            GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);

            calendarList.add(calendar.getTimeInMillis());

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            for (int j = 0; j < dayOfWeek; j++) {
                calendarList.add(EmptyKey.EMPTY);
            }

            for (int j = 1; j <= max; j++) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;

                for (int z = 0; z < winners.size(); z++) {
                    origin[z] = originFormat.parse(winners.get(z).getEventDate());
                    strYear = yearFormat.format(origin[z]);

                    if (Integer.parseInt(monthFormat.format(origin[z])) < 10) {
                        strMonth = monthFormat.format(origin[z]).substring(1);
                    } else {
                        strMonth = monthFormat.format(origin[z]);
                    }
                    if (Integer.parseInt(dayFormat.format(origin[z])) < 10) {
                        strDay = dayFormat.format(origin[z]).substring(1);
                    } else {
                        strDay = dayFormat.format(origin[z]);
                    }
                    if (strDay.equals(String.valueOf(j)) && strMonth.equals(String.valueOf(month)) && strYear.equals(String.valueOf(year))) {
                        if (z >= 1) {
                            calendarList.remove(calendarList.size() - 1);
                            calendarList.add(winners.get(z));
                        } else {
                            calendarList.add(winners.get(z));
                        }
                    } else if (z < 1) {
                        calendarList.add(new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), j));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCalendarList.setValue(calendarList);
    }

}