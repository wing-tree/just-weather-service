package com.wing.tree.just.weather.service.data.datasource.remote

import com.wing.tree.just.weather.service.data.retrofit.service.OpenWeatherService
import com.wing.tree.just.weather.service.domain.model.remote.request.OpenWeatherRequest
import com.wing.tree.just.weather.service.domain.model.remote.response.OpenWeatherResponse
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

typealias QueryMap = Map<String, String>

internal class OpenWeatherDataSourceImpl @Inject constructor(
    private val openWeatherService: OpenWeatherService
) : OpenWeatherDataSource {
    override suspend fun forecast(forecast: OpenWeatherRequest.Forecast): OpenWeatherResponse.Forecast {
        return openWeatherService.forecast(forecast.asQueryMap())
    }

    override suspend fun weather(weather: OpenWeatherRequest.Weather): OpenWeatherResponse.Weather {
        return openWeatherService.weather(weather.asQueryMap())
    }

    private inline fun <reified T : Any> T.asQueryMap() : QueryMap {
        val map = T::class.memberProperties.associateBy { it.name }

        return map.keys.associateWith { map[it]?.get(this).toString() }
    }
}

