package com.newscorp.sampleapp.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val DATE_DEFAULT_FORMAT = "EEE dd MMM"

fun Date.daysAgo(): Int {
    val diffDays = Date().time - time
    return TimeUnit.DAYS.convert(diffDays, TimeUnit.MILLISECONDS).toInt()
}

fun Date.displayDateInFormat(format: String = DATE_DEFAULT_FORMAT): String =
    SimpleDateFormat(format, Locale.US).format(this)