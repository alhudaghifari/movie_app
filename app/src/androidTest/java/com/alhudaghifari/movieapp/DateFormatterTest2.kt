package com.alhudaghifari.movieapp

import android.content.Context
import org.junit.Test
import androidx.test.core.app.ApplicationProvider
import com.alhudaghifari.movieapp.utils.DateFormatterUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

class DateFormatterTest2 {
    val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun testDateFormatter() {
        val result = DateFormatterUtils().getDateFormatting4(context, "2021-04-01")
        val result1 = DateFormatterUtils().getDateFormatting4(context, "2021-05-02")
        val result2 = DateFormatterUtils().getDateFormatting4(context, "2021-08-12")
        val result3 = DateFormatterUtils().getDateFormatting4(context, "2021-12-22")
        val result4 = DateFormatterUtils().getDateFormatting4(context, "2021-02-29")
        assertEquals("01 Apr 2021",result)
        assertEquals("02 May 2021",result1)
        assertEquals("12 Ags 2021",result2)
        assertEquals("22 Dec 2021",result3)
        assertNotEquals("29 Feb 2021",result4)
    }
}