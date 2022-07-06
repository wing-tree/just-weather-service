package com.wing.tree.just.weather.service.domain.model.local.openweather

import com.wing.tree.just.weather.service.domain.constant.EMPTY
import com.wing.tree.just.weather.service.domain.model.remote.response.OpenWeatherResponse

abstract class Weather {
    abstract val icon: String
    abstract val temp: Float
    abstract val feels_like: Float
    abstract val temp_min: Float
    abstract val temp_max: Float

    companion object {
        fun from(weather: OpenWeatherResponse.Weather) = object : Weather() {
            val main = weather.main

            override val icon: String = weather.weather.firstOrNull()?.icon ?: EMPTY
            override val temp: Float = main.temp
            override val feels_like: Float = main.feels_like
            override val temp_min: Float = main.temp_min
            override val temp_max: Float = main.temp_max
        }
    }
}