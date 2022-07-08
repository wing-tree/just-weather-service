package com.wing.tree.just.weather.service.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wing.tree.just.weather.service.domain.model.local.openweather.Weather

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey(autoGenerate = false)
    val dt: Long,
    override val icon: String,
    override val temp: Float,
    override val feels_like: Float,
    override val temp_min: Float,
    override val temp_max: Float
) : Weather()