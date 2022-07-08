package com.wing.tree.just.weather.service.data.dao

import androidx.room.*
import com.wing.tree.just.weather.service.data.entity.Weather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: Weather)

    @Query("DELETE FROM weather")
    suspend fun clear()

    @Query("SELECT dt FROM weather LIMIT 1")
    suspend fun dt(): Long?

    @Query("SELECT * FROM weather LIMIT 1")
    suspend fun weather(): Weather?

    @Transaction
    suspend fun clearAndInsert(weather: Weather) {
        clear()
        insert(weather)
    }
}