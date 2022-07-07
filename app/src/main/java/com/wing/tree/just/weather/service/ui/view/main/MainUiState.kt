package com.wing.tree.just.weather.service.ui.view.main

import androidx.compose.runtime.Immutable
import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast
import com.wing.tree.just.weather.service.domain.model.local.openweather.Weather

data class MainUiState(
    val forecastUiState: ForecastUiState,
    val weatherUiState: WeatherUiState
)

@Immutable
sealed interface ForecastUiState {
    object Loading : ForecastUiState
    data class Content(val forecast: Forecast?) : ForecastUiState
    object Error : ForecastUiState
}

@Immutable
sealed interface WeatherUiState {
    object Loading : WeatherUiState
    data class Content(val weather: Weather?) : WeatherUiState
    object Error : WeatherUiState
}