package com.example.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*


@Entity(tableName = "university")
data class UniversityLocalModel(
    val alpha_two_code: String,
    val country: String,
    @TypeConverters(ListStringConverter::class)
    val domains: List<String>,
    @PrimaryKey
    val name: String,
    val state_province: String?,
    @TypeConverters(ListStringConverter::class)
    val web_pages: List<String>
)

class ListStringConverter {
    @TypeConverter
    fun fromList(domains: List<String>): String {
        return if (domains != null) java.lang.String.join(",", domains) else ""
    }

    @TypeConverter
    fun toList(domainsString: String): List<String> {
        return if (domainsString != null) Arrays.asList(
            *domainsString.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()) else listOf()
    }
}
