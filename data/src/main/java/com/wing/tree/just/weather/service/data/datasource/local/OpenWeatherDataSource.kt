package com.wing.tree.just.weather.service.data.datasource.local

import com.wing.tree.just.weather.service.data.entity.Forecast

interface OpenWeatherDataSource {
    suspend fun clear()
    suspend fun forecast(): Forecast
    suspend fun insert(forecast: Forecast)
}