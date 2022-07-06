package com.wing.tree.just.weather.service.data.repository

import com.wing.tree.just.weather.service.data.mapper.toEntity
import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast
import com.wing.tree.just.weather.service.domain.model.local.openweather.Weather
import com.wing.tree.just.weather.service.domain.model.remote.request.OpenWeatherRequest
import com.wing.tree.just.weather.service.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.wing.tree.just.weather.service.data.datasource.local.OpenWeatherDataSource as LocalOpenWeatherDataSource
import com.wing.tree.just.weather.service.data.datasource.remote.OpenWeatherDataSource as RemoteOpenWeatherDataSource

class OpenWeatherRepositoryImpl @Inject constructor(
    private val localDataSource: LocalOpenWeatherDataSource,
    private val remoteDataSource: RemoteOpenWeatherDataSource
) : OpenWeatherRepository {
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)
    private val primaryKey: Long
        get() = Calendar.getInstance().timeInMillis

    override suspend fun forecast(forecast: OpenWeatherRequest.Forecast): Forecast {
        val dt = localDataSource.dt() ?: Long.MAX_VALUE

        return if (isMoreThanThreeHoursAgo(dt)) {
            Forecast.from(remoteDataSource.forecast(forecast)).also {
                coroutineScope.launch { localDataSource.clearAndInsert(it.toEntity(primaryKey)) }
            }
        } else {
            localDataSource.forecast() ?: Forecast.from(remoteDataSource.forecast(forecast)).also {
                coroutineScope.launch { localDataSource.clearAndInsert(it.toEntity(primaryKey)) }
            }
        }
    }

    override suspend fun weather(weather: OpenWeatherRequest.Weather): Weather {
        return Weather.from(remoteDataSource.weather(weather))
    }

    override fun clear() {
        job.cancel()
    }

    private fun isMoreThanOneHoursAgo(dt: Long): Boolean {
        return with(Calendar.getInstance().timeInMillis.minus(dt)) {
            TimeUnit.MILLISECONDS.toHours(this) > 0L
        }
    }

    private fun isMoreThanThreeHoursAgo(dt: Long): Boolean {
        return with(Calendar.getInstance().timeInMillis.minus(dt)) {
            TimeUnit.MILLISECONDS.toHours(this) > 2L
        }
    }
}