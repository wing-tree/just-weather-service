package com.wing.tree.just.weather.service.data.mapper

import com.wing.tree.just.weather.service.data.entity.Weather as Entity
import com.wing.tree.just.weather.service.domain.model.local.openweather.Weather

fun Weather.toEntity(dt: Long) = Entity(
    dt = dt,
    icon = icon,
    temp = temp,
    feels_like = feels_like,
    temp_min = temp_min,
    temp_max = temp_max
)