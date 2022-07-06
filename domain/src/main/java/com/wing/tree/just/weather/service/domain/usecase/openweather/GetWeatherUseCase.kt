package com.wing.tree.just.weather.service.domain.usecase.openweather

import com.wing.tree.just.weather.service.domain.model.local.location.LatLng
import com.wing.tree.just.weather.service.domain.model.local.openweather.Weather
import com.wing.tree.just.weather.service.domain.model.remote.request.OpenWeatherRequest
import com.wing.tree.just.weather.service.domain.model.remote.response.OpenWeatherResponse
import com.wing.tree.just.weather.service.domain.repository.OpenWeatherRepository
import com.wing.tree.just.weather.service.domain.usecase.core.CoroutineUseCase
import com.wing.tree.just.weather.service.domain.usecase.core.IOCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: OpenWeatherRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<LatLng, Weather>(coroutineDispatcher) {
    override suspend fun execute(parameter: LatLng): Weather {
        return repository.weather(OpenWeatherRequest.Weather(parameter.latitude, parameter.latitude))
    }
}