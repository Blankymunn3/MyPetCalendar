package io.kim_kong.mypetcalendar.viewmodel

import androidx.lifecycle.ViewModel
import io.kim_kong.mypetcalendar.livedata.CalendarLiveData
import io.kim_kong.mypetcalendar.util.CalendarDateFormat
import java.util.*


class CalendarMainViewModel : ViewModel() {
    var mTitleMonth = CalendarLiveData<String>()
    var mTitleYear = CalendarLiveData<String>()
    fun initMainCalendarTitle(cal: GregorianCalendar) {
        setTitle(cal.timeInMillis)
    }

    fun setTitle(time: Long) {
        mTitleMonth.value = CalendarDateFormat.getDate(
            time,
            CalendarDateFormat.CALENDAR_HAEDER_MONTH_FORMAT
        )
        mTitleYear.value = CalendarDateFormat.getDate(
            time,
            CalendarDateFormat.CALENDAR_HEADER_YEAR_FORMAT
        )
    }
}
