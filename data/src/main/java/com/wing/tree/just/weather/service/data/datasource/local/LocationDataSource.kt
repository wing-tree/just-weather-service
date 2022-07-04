package com.wing.tree.just.weather.service.data.datasource.local

import android.Manifest
import androidx.annotation.RequiresPermission
import com.wing.tree.just.weather.service.domain.model.local.location.LatLng
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun latLng(): Flow<LatLng>
}