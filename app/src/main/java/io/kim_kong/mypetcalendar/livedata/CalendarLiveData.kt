package io.kim_kong.mypetcalendar.livedata

import androidx.lifecycle.MutableLiveData


class CalendarLiveData<T> : MutableLiveData<T> {
    constructor() {}
    constructor(value: T) {
        setValue(value)
    }
}