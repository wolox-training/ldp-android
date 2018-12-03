package ar.com.wolox.android.example.utils

import org.joda.time.format.DateTimeFormat
import org.ocpsoft.prettytime.PrettyTime

object DateUtils {

    fun toDuration(date: String): String {
        val localDate: java.util.Date = DateTimeFormat
                .forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withLocale(java.util.Locale.getDefault())
                .parseLocalDate(date)
                .toDate()

        return PrettyTime().format(localDate)
    }
}