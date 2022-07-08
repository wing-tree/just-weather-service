package com.wing.tree.just.weather.service.data.dao

import androidx.room.*
import com.wing.tree.just.weather.service.data.entity.Forecast

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
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