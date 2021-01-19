package org.akefestival.core.util

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

fun String.toLongDate(format: String = DATE_FORMAT): Long {
    return this.toCalendar(format).timeInMillis
}

fun String.toCalendar(format: String = DATE_FORMAT): Calendar {
    return Calendar.getInstance().apply {
        time = SimpleDateFormat(format, Locale.ENGLISH).parse(this@toCalendar)!!
    }
}

fun Long.toTimeString(format: String = "hh:mm a"): String {
    return SimpleDateFormat(format, Locale.ENGLISH).format(Date(this))
}

fun Long.toDateString(format: String = DATE_FORMAT): String {
    return SimpleDateFormat(format, Locale.ENGLISH).format(
        Calendar.getInstance().apply { timeInMillis = this@toDateString })
}

val Calendar.isToday: Boolean
    get() {
        val today = Calendar.getInstance()
        return this.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH) && this.get(
            Calendar.MONTH
        ) == today.get(Calendar.MONTH)
    }