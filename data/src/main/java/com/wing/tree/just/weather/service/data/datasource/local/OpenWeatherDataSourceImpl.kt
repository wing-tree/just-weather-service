package com.wing.tree.just.weather.service.data.datasource.local

import com.wing.tree.just.weather.service.data.database.Database
import com.wing.tree.just.weather.service.data.entity.Forecast
import javax.inject.Inject

class OpenWeatherDataSourceImpl @Inject constructor(database: Database) : OpenWeatherDataSource {
    private val forecastDao = database.forecastDao()

    override suspend fun clear() {
        forecastDao.clear()
    }

    override suspend fun insert(forecast: Forecast) {
        forecastDao.insert(forecast)
    }

    override suspend fun forecast(): Forecast? {
        return forecastDao.get()
    }
}