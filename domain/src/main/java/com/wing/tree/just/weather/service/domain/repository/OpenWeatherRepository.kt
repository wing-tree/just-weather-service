package com.wing.tree.just.weather.service.domain.repository

import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast
import com.wing.tree.just.weather.service.domain.model.local.openweather.Weather
import com.wing.tree.just.weather.service.domain.model.remote.request.OpenWeatherRequest

interface OpenWeatherRepository {
    suspend fun forecast(forecast: OpenWeatherRequest.Forecast): Forecast
    suspend fun weather(weather: OpenWeatherRequest.Weather): Weather
    fun clear()
}