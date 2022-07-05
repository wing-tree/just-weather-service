package com.wing.tree.just.weather.service.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast
import com.wing.tree.just.weather.service.domain.model.remote.response.OpenWeatherResponse

@Entity(tableName = "forecast")
data class Forecast(
    @PrimaryKey(autoGenerate = false)
    val dt: Long,
    override val list: List<Item>
) : Forecast() {
    data class Item(
        override val dt: Long,
        override val temp: Float,
        override val feels_like: Float,
        override val temp_min: Float,
        override val temp_max: Float,
        override val pressure: Int,
        override val humidity: Int,
        override val weather: OpenWeatherResponse.Forecast.Item.Weather?
    ) : Forecast.Item()
}