package com.wing.tree.just.weather.service.data.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.wing.tree.just.weather.service.data.entity.Forecast

class TypeConverters {
    @TypeConverter
    fun listToJson(value: List<Forecast.Item>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Forecast.Item>::class.java).toList()
}