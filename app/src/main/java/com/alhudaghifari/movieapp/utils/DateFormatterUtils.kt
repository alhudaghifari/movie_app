package com.alhudaghifari.movieapp.utils

import android.content.Context
import com.alhudaghifari.movieapp.R
import java.text.SimpleDateFormat
import java.util.*

class DateFormatterUtils {
    fun getDateFormatting4(context: Context, myDate: String): String {
        try {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(myDate)
            val dateNumber = SimpleDateFormat("dd", Locale.ENGLISH)
            val monthNumber = SimpleDateFormat("MM", Locale.ENGLISH)
            val yearNumber = SimpleDateFormat("yyyy", Locale.ENGLISH)
            val day = dateNumber.format(date!!)
            val month = monthNumber.format(date)
            val year = yearNumber.format(date)

            val fulldate = "$day ${getMonthNameAbbreviationFromStringNumber(context, month)} $year"
            return fulldate
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }


    /**
     * input example : 08
     * output example : Ags (based on language used)
     */
    fun getMonthNameAbbreviationFromStringNumber(context: Context, month: String): String {
        return when (month) {
            "01" -> context.resources.getString(R.string.month_january_simple)
            "02" -> context.resources.getString(R.string.month_february_simple)
            "03" -> context.resources.getString(R.string.month_march_simple)
            "04" -> context.resources.getString(R.string.month_april_simple)
            "05" -> context.resources.getString(R.string.month_may_simple)
            "06" -> context.resources.getString(R.string.month_june_simple)
            "07" -> context.resources.getString(R.string.month_july_simple)
            "08" -> context.resources.getString(R.string.month_augusts_simple)
            "09" -> context.resources.getString(R.string.month_september_simple)
            "10" -> context.resources.getString(R.string.month_october_simple)
            "11" -> context.resources.getString(R.string.month_november_simple)
            "12" -> context.resources.getString(R.string.month_december_simple)
            "1" -> context.resources.getString(R.string.month_january_simple)
            "2" -> context.resources.getString(R.string.month_february_simple)
            "3" -> context.resources.getString(R.string.month_march_simple)
            "4" -> context.resources.getString(R.string.month_april_simple)
            "5" -> context.resources.getString(R.string.month_may_simple)
            "6" -> context.resources.getString(R.string.month_june_simple)
            "7" -> context.resources.getString(R.string.month_july_simple)
            "8" -> context.resources.getString(R.string.month_augusts_simple)
            "9" -> context.resources.getString(R.string.month_september_simple)
            else -> ""
        }
    }

}