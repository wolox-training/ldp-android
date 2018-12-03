package ar.com.wolox.android.example.utils

import android.content.Context
import ar.com.wolox.android.R
import org.joda.time.format.DateTimeFormat
import org.ocpsoft.prettytime.PrettyTime
import java.util.Locale

object DateUtils {

    fun toDuration(date: String, context: Context): String {
        val localDate = DateTimeFormat
                .forPattern(context.getString(R.string.app_date_format))
                .withLocale(Locale.getDefault())
                .parseLocalDate(date)
                .toDate()

        return PrettyTime().format(localDate)
    }
}