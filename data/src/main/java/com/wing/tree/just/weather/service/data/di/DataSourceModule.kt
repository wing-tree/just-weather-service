package com.wing.tree.just.weather.service.data.di

import com.wing.tree.just.weather.service.data.datasource.local.LocationDataSource
import com.wing.tree.just.weather.service.data.datasource.local.LocationDataSourceImpl
import com.wing.tree.just.weather.service.data.datasource.local.OpenWeatherDataSource as LocalOpenWeatherDataSource
import com.wing.tree.just.weather.service.data.datasource.local.OpenWeatherDataSourceImpl as LocalOpenWeatherDataSourceImpl
import com.wing.tree.just.weather.service.data.datasource.remote.OpenWeatherDataSource as RemoteOpenWeatherDataSource
import com.wing.tree.just.weather.service.data.datasource.remote.OpenWeatherDataSourceImpl as RemoteOpenWeatherDataSourceImpl
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
    abstract fun bindsLocalOpenWeatherDataSource(dataSource: LocalOpenWeatherDataSourceImpl): LocalOpenWeatherDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteOpenWeatherDataSource(dataSource: RemoteOpenWeatherDataSourceImpl): RemoteOpenWeatherDataSource
}