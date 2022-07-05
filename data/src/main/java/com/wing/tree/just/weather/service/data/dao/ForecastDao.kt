package com.wing.tree.just.weather.service.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.wing.tree.just.weather.service.data.entity.Forecast

@Dao
interface ForecastDao {
    @Insert
    suspend fun insert(forecast: Forecast)

    @Query("DELETE FROM forecast")
    suspend fun clear()

    @Query("SELECT dt FROM forecast LIMIT 1")
    suspend fun dt(): Long?

    @Query("SELECT * FROM forecast LIMIT 1")
    suspend fun forecast(): Forecast?

    @Transaction
    suspend fun clearAndInsert(forecast: Forecast) {
        clear()
        insert(forecast)
    }
}