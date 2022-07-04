package com.wing.tree.just.weather.service.domain.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import com.wing.tree.just.weather.service.domain.model.local.location.LatLng
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun latLng(): Flow<LatLng>
}