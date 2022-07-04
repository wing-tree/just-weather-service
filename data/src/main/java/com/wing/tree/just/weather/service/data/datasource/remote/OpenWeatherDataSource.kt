package com.wing.tree.just.weather.service.data.datasource.remote

import com.wing.tree.just.weather.service.domain.model.remote.request.OpenWeatherRequest
import com.wing.tree.just.weather.service.domain.model.remote.response.OpenWeatherResponse

interface OpenWeatherDataSource {
    suspend fun forecast(forecast: OpenWeatherRequest.Forecast): OpenWeatherResponse.Forecast
    suspend fun weather(weather: OpenWeatherRequest.Weather): OpenWeatherResponse.Weather
}