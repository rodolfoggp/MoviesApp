package com.rodolfogusson.testepag.infrastructure.data.persistence.database

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun fromString(string: String?): LocalDate? {
        return string?.let {  LocalDate.parse(string) }
    }

    @TypeConverter
    fun dateToString(date: LocalDate?): String? {
        return date?./*atStartOfDay(ZoneId.systemDefault())?.toInstant()?.*/toString()
    }
}