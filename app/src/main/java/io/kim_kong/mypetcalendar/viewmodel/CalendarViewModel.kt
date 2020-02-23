package io.kim_kong.mypetcalendar.viewmodel

import androidx.lifecycle.ViewModel
import io.kim_kong.mypetcalendar.livedata.CalendarLiveData
import java.util.*


class CalendarViewModel : ViewModel() {
    var mCalendar: CalendarLiveData<Calendar> = CalendarLiveData<Calendar>()
    fun setCalendar(calendar: Calendar) {
        mCalendar.value = calendar
    }
}
