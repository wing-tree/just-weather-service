package com.wing.tree.just.weather.service.data.di

import com.wing.tree.just.weather.service.data.datasource.local.LocationDataSource
import com.wing.tree.just.weather.service.data.datasource.local.LocationDataSourceImpl
import com.wing.tree.just.weather.service.data.datasource.remote.OpenWeatherDataSource
import com.wing.tree.just.weather.service.data.datasource.remote.OpenWeatherDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsLocationDataSource(dataSource: LocationDataSourceImpl): LocationDataSource

    @Binds
    @Singleton
    abstract fun bindsOpenWeatherDataSource(dataSource: OpenWeatherDataSourceImpl): OpenWeatherDataSource
}