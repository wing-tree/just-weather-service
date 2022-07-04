package com.wing.tree.just.weather.service.data.di

import com.wing.tree.just.weather.service.data.repository.LocationRepositoryImpl
import com.wing.tree.just.weather.service.data.repository.OpenWeatherRepositoryImpl
import com.wing.tree.just.weather.service.data.retrofit.service.OpenWeatherService
import com.wing.tree.just.weather.service.domain.repository.LocationRepository
import com.wing.tree.just.weather.service.domain.repository.OpenWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [RepositoryModule.ServiceModule::class])
internal abstract class RepositoryModule {
    @Binds
    abstract fun bindsLocationRepository(
        repository: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    abstract fun bindsOpenWeatherRepository(
        repository: OpenWeatherRepositoryImpl
    ): OpenWeatherRepository

    @InstallIn(SingletonComponent::class)
    @Module
    internal object ServiceModule {
        @Provides
        @Singleton
        fun providesOpenWeatherService(
            retrofit: Retrofit
        ): OpenWeatherService {
            return retrofit.create(OpenWeatherService::class.java)
        }
    }
}