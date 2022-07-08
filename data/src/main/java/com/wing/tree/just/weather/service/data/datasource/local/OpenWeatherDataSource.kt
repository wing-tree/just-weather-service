package com.wing.tree.just.weather.service.data.datasource.local

import com.wing.tree.just.weather.service.data.entity.Forecast

interface OpenWeatherDataSource {
    suspend fun clearAndInsert(forecast: Forecast)
    suspend fun dt(): Long?
    suspend fun forecast(): Forecast?
}