package com.wing.tree.just.weather.service.data.repository

import com.wing.tree.just.weather.service.data.datasource.remote.OpenWeatherDataSource
import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast
import com.wing.tree.just.weather.service.domain.model.remote.request.OpenWeatherRequest
import com.wing.tree.just.weather.service.domain.model.remote.response.OpenWeatherResponse
import com.wing.tree.just.weather.service.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(private val dataSource: OpenWeatherDataSource) : OpenWeatherRepository {
    override suspend fun forecast(forecast: OpenWeatherRequest.Forecast): Forecast {
        return Forecast.from(dataSource.forecast(forecast))
    }

    override suspend fun weather(weather: OpenWeatherRequest.Weather): OpenWeatherResponse.Weather {
        return dataSource.weather(weather)
    }
}