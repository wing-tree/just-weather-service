package com.wing.tree.just.weather.service.data.mapper

import com.wing.tree.just.weather.service.data.entity.Forecast as Entity
import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast

fun Forecast.toEntity(dt: Long) = Entity(
    dt = dt,
    list = list.map { it.toEntity() }
)

fun Forecast.Item.toEntity() = Entity.Item(
    dt = dt,
    temp = temp,
    feels_like = feels_like,
    temp_min = temp_min,
    temp_max = temp_max,
    pressure = pressure,
    humidity = humidity,
    weather = weather
)