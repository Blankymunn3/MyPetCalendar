package io.kim_kong.mypetcalendar.util

import java.text.SimpleDateFormat
import java.util.*

class CalendarDateFormat {
    companion object CalendarDateFormat {
        const val CALENDAR_HEADER_FORMAT = "yyyy년 MM월"
        const val CALENDAR_HEADER_YEAR_FORMAT = "yyyy."
        const val CALENDAR_HAEDER_MONTH_FORMAT = "MM"
        const val DAY_FORMAT = "d"
        fun getDate(date: Long, pattern: String?): String {
            return try {
                val formatter = SimpleDateFormat(pattern, Locale.getDefault())
                val d = Date(date)
                formatter.format(d).toUpperCase()
            } catch (e: Exception) {
                " "
            }
        }
    }
}