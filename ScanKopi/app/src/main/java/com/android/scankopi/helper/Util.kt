package com.android.scankopi.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Util {
    fun getTimeStamp(): String {
        val timeStampFormat = "yyyy-MM-dd-HH-mm-ss-SSS"
        return SimpleDateFormat(timeStampFormat, Locale.getDefault()).format(Date())
    }

    fun getDateTime(date: String): String {
        val year = date.slice(0..3)
        val month = date.slice(5..6)
        val day = date.slice(8..9)
        val hour = date.slice(11..12)
        val min = date.slice(14..15)

        return "$day-$month-$year | $hour:$min"
    }
}