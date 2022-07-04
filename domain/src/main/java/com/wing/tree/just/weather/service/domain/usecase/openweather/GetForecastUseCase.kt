package com.wing.tree.just.weather.service.domain.usecase.openweather

import com.wing.tree.just.weather.service.domain.model.local.location.LatLng
import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast
import com.wing.tree.just.weather.service.domain.model.remote.request.OpenWeatherRequest
import com.wing.tree.just.weather.service.domain.repository.OpenWeatherRepository
import com.wing.tree.just.weather.service.domain.usecase.core.CoroutineUseCase
import com.wing.tree.just.weather.service.domain.usecase.core.IOCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: OpenWeatherRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<LatLng, Forecast>(coroutineDispatcher) {
    override suspend fun execute(parameter: LatLng): Forecast {
        return repository.forecast(OpenWeatherRequest.Forecast(parameter.latitude, parameter.latitude))
    }
}