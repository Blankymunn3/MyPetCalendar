package io.kim_kong.mypetcalendar.viewmodel

import androidx.lifecycle.ViewModel
import io.kim_kong.mypetcalendar.livedata.CalendarLiveData


class CalendarImageViewModel : ViewModel() {
    var mImage = CalendarLiveData<String>()
    fun setmImage(image: String) {
        mImage.value = image
    }
}
