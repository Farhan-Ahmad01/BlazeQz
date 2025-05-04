package com.farhan.blazeqz.data.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class OptionListConverters {

    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun fromStringToList(stringList: String): List<String> {
        return Json.decodeFromString(stringList)
    }

}