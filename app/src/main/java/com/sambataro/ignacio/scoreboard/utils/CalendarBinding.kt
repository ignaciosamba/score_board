package com.sambataro.ignacio.scoreboard.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sambataro.ignacio.scoreboard.domain.DayCalendarInfo

@BindingAdapter("dayNameCalendar")
fun TextView.setDayName(item: DayCalendarInfo?) {
    item?.let {
        text =  item.dayName
    }
}

@BindingAdapter("dateInfoCalendar")
fun TextView.setDateInfo(item: DayCalendarInfo?) {
    item?.let {
        text =  item.dateInfo
    }
}