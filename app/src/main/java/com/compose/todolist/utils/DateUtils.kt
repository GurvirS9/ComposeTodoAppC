package com.compose.todolist.utils

import android.util.Log
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val TAG = "DateUtils"

object DateUtils {
    private val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    // Tuesday. Feb 30, 2077 12:23:56 AM
    private val outputFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a")
    private val istZone = ZoneId.of("Asia/Kolkata")

    fun convertUTCtoIST(utcDateStr: String): String {
        return try {
            val localDateTime = LocalDateTime.parse(utcDateStr, inputFormatter)
            val utcDateTime = localDateTime.atZone(ZoneId.of("UTC"))
            val istDateTime = utcDateTime.withZoneSameInstant(istZone)
            outputFormatter.format(istDateTime)
        } catch (e: Exception) {
            Log.e(TAG, "Error converting date: $utcDateStr", e)
            utcDateStr
        }
    }
}
