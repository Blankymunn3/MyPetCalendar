package io.kim_kong.mypetcalendar.viewmodel

import androidx.lifecycle.ViewModel
import io.kim_kong.mypetcalendar.livedata.CalendarLiveData


class CalendarHeaderViewModel : ViewModel() {
    var mHeaderDate: CalendarLiveData<Long?>? = CalendarLiveData()
    fun setHeaderDate(headerDate: Long) {
        mHeaderDate?.value = headerDate
    }
}