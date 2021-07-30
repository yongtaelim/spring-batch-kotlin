package me.practice.kotlinbatch.common.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by LYT to 2021/07/30
 */
class DateUtils {
    companion object {
        const val QUERY_DEFAULT_DATE_FORMAT = "yyyy-MM-dd"

        fun getFormattedDate(date: LocalDate): String =
            date.format(DateTimeFormatter.ofPattern(QUERY_DEFAULT_DATE_FORMAT))

        fun getFormattedDate(dateTime: LocalDateTime): String =
            dateTime.format(DateTimeFormatter.ofPattern(QUERY_DEFAULT_DATE_FORMAT))

        fun nowMinusDays(minusDays: Long): LocalDate =
            LocalDate.now().minusDays(minusDays)
    }
}