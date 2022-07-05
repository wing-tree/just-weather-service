package com.wing.tree.just.weather.service.domain.model.remote.request

import com.wing.tree.just.weather.service.domain.constant.Key

sealed class OpenWeatherRequest {
    enum class Units(val value: String) {
        @Suppress("unused")
        Standard("standard"),
        Metric("metric"),
        @Suppress("unused")
        Imperial("imperial")
    }

    data class Forecast(
        val lat: Double,
        val lon: Double,
        val appid: String = Key,
        val units: String = Units.Metric.value
    ) : OpenWeatherRequest()

    data class Weather(
        val lat: Double,
        val lon: Double,
        val appid: String = Key,
        val units: String = Units.Metric.value
    ) : OpenWeatherRequest()
}