package com.wing.tree.just.weather.service.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wing.tree.just.weather.service.data.dao.ForecastDao
import com.wing.tree.just.weather.service.data.dao.WeatherDao
import com.wing.tree.just.weather.service.data.entity.Forecast
import com.wing.tree.just.weather.service.data.entity.Weather
import com.wing.tree.just.weather.service.data.typeconverter.TypeConverters

@androidx.room.Database(
    entities = [Forecast::class, Weather::class],
    exportSchema = false,
    version = 1
)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class Database: RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        private const val PACKAGE_NAME = "com.wing.tree.just.weather.service.data.entity.Forecast"
        private const val CLASS_NAME = "Database"
        private const val NAME = "$PACKAGE_NAME.$CLASS_NAME"
        private const val VERSION = "1.0.0"

        @Volatile
        private var INSTANCE: Database? = null

        fun instance(context: Context): Database {
            synchronized(this) {
                return INSTANCE ?: run {
                    Room.databaseBuilder(
                        context.applicationContext,
                        Database::class.java,
                        "$NAME.$VERSION"
                    )
                        .build()
                        .also {
                            INSTANCE = it
                        }
                }
            }
        }
    }
}