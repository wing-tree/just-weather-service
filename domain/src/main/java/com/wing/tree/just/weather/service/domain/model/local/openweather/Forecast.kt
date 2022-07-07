package com.wing.tree.just.weather.service.domain.model.local.openweather

import com.wing.tree.just.weather.service.domain.extension.date
import com.wing.tree.just.weather.service.domain.extension.month
import com.wing.tree.just.weather.service.domain.extension.year
import com.wing.tree.just.weather.service.domain.model.remote.response.OpenWeatherResponse
import java.time.LocalDate
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

        private val calendar: Calendar
            get() = Calendar.getInstance(Locale.getDefault())
                .apply {
                    timeInMillis = dtInMilliseconds
                }

        fun calendarEquals(calendar: Calendar) =
            calendar.year == this.calendar.year &&
                calendar.month == this.calendar.month &&
                calendar.date == this.calendar.date

        companion object {
            fun from(item: OpenWeatherResponse.Forecast.Item): Item {
                val main = item.main
                val weather = item.weather.firstOrNull()

                return object : Item() {
                    override val dt: Long = item.dt
                    override val temp: Float = main.temp
                    override val feels_like: Float = main.feels_like
                    override val temp_min: Float = main.temp_min
                    override val temp_max: Float = main.temp_max
                    override val pressure: Int = main.pressure
                    override val humidity: Int = main.humidity
                    override val weather: OpenWeatherResponse.Forecast.Item.Weather? = weather
                }
            }
        }
    }

    companion object {
        fun from(forecast: OpenWeatherResponse.Forecast) = object : Forecast() {
            override val list: List<Item> = forecast.list.map { Item.from(it) }
        }
    }
}