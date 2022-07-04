package com.wing.tree.just.weather.service.data.model

import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast
import com.wing.tree.just.weather.service.domain.model.remote.response.OpenWeatherResponse

data class Forecast(
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
    ) : Forecast.Item() {
        companion object {
            fun from(item: OpenWeatherResponse.Forecast.Item): Item {
                val main = item.main
                val weather = item.weather.firstOrNull()

                return Item(
                    dt = item.dt,
                    temp = main.temp,
                    feels_like = main.feels_like,
                    temp_min = main.temp_min,
                    temp_max = main.temp_max,
                    pressure = main.pressure,
                    humidity = main.humidity,
                    weather = weather
                )
            }
        }
    }

    companion object {
        fun from(forecast: OpenWeatherResponse.Forecast) = Forecast(
            list = forecast.list.map { Item.from(it) }
        )
    }
}