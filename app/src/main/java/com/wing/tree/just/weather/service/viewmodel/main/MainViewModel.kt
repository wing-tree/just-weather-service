package com.wing.tree.just.weather.service.viewmodel.main

import android.Manifest
import android.app.Application
import android.location.Address
import android.location.Geocoder
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wing.tree.just.weather.service.domain.exception.OnCanceledException
import com.wing.tree.just.weather.service.domain.model.local.location.LatLng
import com.wing.tree.just.weather.service.domain.repository.LocationRepository
import com.wing.tree.just.weather.service.domain.usecase.core.Result
import com.wing.tree.just.weather.service.domain.usecase.openweather.GetForecastUseCase
import com.wing.tree.just.weather.service.domain.usecase.openweather.GetWeatherUseCase
import com.wing.tree.just.weather.service.ui.view.main.ForecastState
import com.wing.tree.just.weather.service.ui.view.main.MainState
import com.wing.tree.just.weather.service.ui.view.main.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val getForecastUseCase: GetForecastUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    application: Application
) : AndroidViewModel(application) {
    private val latLng = MutableStateFlow<LatLng?>(null)

    private val forecast = channelFlow {
        latLng.collectLatest { latLng ->
            latLng?.let { send(getForecastUseCase(it)) }
        }
    }

    private val weather = channelFlow {
        latLng.collectLatest { latLng ->
            latLng?.let { send(getWeatherUseCase(it)) }
        }
    }

    val uiState: StateFlow<MainState> = combine(
        forecast,
        weather
    ) { forecast, weather ->
        val forecastState: ForecastState = when (forecast) {
            is Result.Loading -> ForecastState.Loading
            is Result.Success -> ForecastState.Content(forecast.data)
            is Result.Failure -> ForecastState.Error
        }

        val weatherState: WeatherState = when (weather) {
            is Result.Loading -> WeatherState.Loading
            is Result.Success -> WeatherState.Content(weather.data)
            is Result.Failure -> WeatherState.Error
        }

        MainState(
            forecastState = forecastState,
            weatherState = weatherState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = MainState(
            forecastState = ForecastState.Loading,
            weatherState = WeatherState.Loading
        )
    )

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun load() {
        viewModelScope.launch {
            try {
                latLng.emit(locationRepository.latLng().firstOrNull())
            } catch (onCanceledException: OnCanceledException) {

            }
        }
    }

    private fun address(latLng: LatLng): Address? {
        val geocoder = Geocoder(getApplication(), Locale.getDefault())
        val latitude = latLng.latitude
        val longitude = latLng.longitude

        return geocoder.getFromLocation(latitude, longitude, 1).firstOrNull()
    }
}