package com.wing.tree.just.weather.service.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import com.wing.tree.just.weather.service.data.datasource.local.LocationDataSource
import com.wing.tree.just.weather.service.domain.model.local.location.LatLng
import com.wing.tree.just.weather.service.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(private val dataSource: LocationDataSource) : LocationRepository {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    override fun latLng(): Flow<LatLng> {
        return dataSource.latLng()
    }
}