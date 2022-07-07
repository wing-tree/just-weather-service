package com.wing.tree.just.weather.service.ui.view.main

import androidx.compose.runtime.Immutable
import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast
import com.wing.tree.just.weather.service.domain.model.local.openweather.Weather

data class MainState(
    val forecastState: ForecastState,
    val weatherState: WeatherState
)

@Immutable
sealed interface ForecastState {
    object Loading : ForecastState
    data class Content(val forecast: Forecast?) : ForecastState
    object Error : ForecastState
}

@Immutable
sealed interface WeatherState {
    object Loading : WeatherState
    data class Content(val weather: Weather?) : WeatherState
    object Error : WeatherState
}