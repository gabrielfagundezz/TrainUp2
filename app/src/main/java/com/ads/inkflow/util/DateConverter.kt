package com.ads.inkflow.util

import androidx.room.TypeConverter
import java.sql.Date
import java.sql.Timestamp

class DateConverter {
    @TypeConverter
    fun timeStampFromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun dateFromTimeStamp(timestamp: Long) : Date?{
        return Date(timestamp)
    }
}