package com.wing.tree.just.weather.service.data.datasource.local

import com.wing.tree.just.weather.service.data.entity.Forecast
import com.wing.tree.just.weather.service.data.entity.Type
import com.wing.tree.just.weather.service.data.entity.Weather

interface OpenWeatherDataSource {
    suspend fun clearAndInsert(forecast: Forecast)
    suspend fun clearAndInsert(weather: Weather)
    suspend fun dt(type: Type): Long?
    suspend fun forecast(): Forecast?
    suspend fun weather(): Weather?
}