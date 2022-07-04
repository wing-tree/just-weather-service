package com.wing.tree.just.weather.service.domain.model.local.openweather

import com.wing.tree.just.weather.service.domain.core.date
import com.wing.tree.just.weather.service.domain.core.month
import com.wing.tree.just.weather.service.domain.core.year
import com.wing.tree.just.weather.service.domain.model.remote.response.OpenWeatherResponse
import java.util.*

abstract class Forecast {
    abstract val list: List<Item>

    abstract class Item {
        abstract val dt: Long
        abstract val temp: Float
        abstract val feels_like: Float
        abstract val temp_min: Float
        abstract val temp_max: Float
        abstract val pressure: Int
        abstract val humidity: Int
        abstract val weather: OpenWeatherResponse.Forecast.Item.Weather?

        val dtInMilliseconds: Long
            get() = dt.times(1000L)

        val year: Int get() = Calendar.getInstance().apply{ timeInMillis = dtInMilliseconds }.year
        val month: Int get() = Calendar.getInstance().apply{ timeInMillis = dtInMilliseconds }.month
        val date: Int get() = Calendar.getInstance().apply{ timeInMillis = dtInMilliseconds }.date

        fun dateEquals(calendar: Calendar) = calendar.year == year &&
                calendar.month == month &&
                calendar.date == date
    }
}