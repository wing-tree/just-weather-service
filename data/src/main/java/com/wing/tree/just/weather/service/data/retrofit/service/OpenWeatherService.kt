package com.wing.tree.just.weather.service.data.retrofit.service

import com.wing.tree.just.weather.service.domain.model.remote.response.OpenWeatherResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface OpenWeatherService {
    @GET("forecast")
    suspend fun forecast(@QueryMap queryMap: Map<String, String>): OpenWeatherResponse.Forecast

    @GET("weather")
    suspend fun weather(@QueryMap queryMap: Map<String, String>): OpenWeatherResponse.Weather
}