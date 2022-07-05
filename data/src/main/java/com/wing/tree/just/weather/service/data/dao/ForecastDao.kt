package com.wing.tree.just.weather.service.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wing.tree.just.weather.service.data.entity.Forecast

@Dao
interface ForecastDao {
    @Insert
    suspend fun insert(forecast: Forecast)

    @Query("DELETE FROM forecast")
    suspend fun clear()

    @Query("SELECT * FROM forecast LIMIT 1")
    suspend fun get(): Forecast
}