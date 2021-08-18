package com.danielpasser.randomusers.utils

import android.util.Log
import com.danielpasser.randomusers.models.Dob
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object Utils {
    fun daysUntilBirthDay(dob: Dob?): String {
        val str = dob?.date?.split("T")?.get(0)
        var birthday = LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val currentDay = LocalDate.now()
        birthday = birthday.withYear(currentDay.year)
        if (currentDay.isAfter(birthday)) birthday = birthday.withYear(birthday.year + 1)
        return currentDay.until(birthday, ChronoUnit.DAYS).toString()
    }
}