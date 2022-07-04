package com.wing.tree.just.weather.service.viewmodel

import android.Manifest
import android.app.Application
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wing.tree.just.weather.service.core.ONE
import com.wing.tree.just.weather.service.domain.exception.OnCanceledException
import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast
import com.wing.tree.just.weather.service.domain.repository.LocationRepository
import com.wing.tree.just.weather.service.domain.usecase.core.getOrNull
import com.wing.tree.just.weather.service.domain.usecase.openweather.GetForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val getForecastUseCase: GetForecastUseCase,
    application: Application
) : AndroidViewModel(application) {
    private var _forecast = MutableLiveData<Forecast>()
    val forecast: LiveData<Forecast> get() = _forecast

    val today: Calendar get() = Calendar.getInstance()
    val tomorrow: Calendar get() = today.apply { add(Calendar.DATE, ONE) }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun forecast() {
        viewModelScope.launch {
            try {
                with(locationRepository.latLng().first()) {
                    _forecast.value = getForecastUseCase(this).getOrNull()
                }
            } catch (onCanceledException: OnCanceledException) {

            }
        }
    }
}