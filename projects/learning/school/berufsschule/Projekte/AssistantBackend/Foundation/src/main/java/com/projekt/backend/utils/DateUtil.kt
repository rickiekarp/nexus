package com.projekt.backend.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun getDate(format: String) : String {
            val dateFormat = SimpleDateFormat(format)
            val dateToday = Calendar.getInstance().time //Get date using calendar object.
            return dateFormat.format(dateToday) //returns date string
        }
    }
}