package com.wing.tree.just.weather.service.data.datasource.local

import com.wing.tree.just.weather.service.data.database.Database
import com.wing.tree.just.weather.service.data.entity.Forecast
import com.wing.tree.just.weather.service.data.entity.Type
import com.wing.tree.just.weather.service.data.entity.Weather
import javax.inject.Inject

class OpenWeatherDataSourceImpl @Inject constructor(database: Database) : OpenWeatherDataSource {
    private val forecastDao = database.forecastDao()
    private val weatherDao = database.weatherDao()

    override suspend fun clearAndInsert(forecast: Forecast) {
        forecastDao.clearAndInsert(forecast)
    }

    override suspend fun clearAndInsert(weather: Weather) {
        weatherDao.clearAndInsert(weather)
    }

    override suspend fun dt(type: Type): Long? {
        return when(type) {
            Type.Forecast -> forecastDao.dt()
            Type.Weather -> weatherDao.dt()
        }
    }

    override suspend fun forecast(): Forecast? {
        return forecastDao.forecast()
    }

    override suspend fun weather(): Weather? {
        return weatherDao.weather()
    }
}