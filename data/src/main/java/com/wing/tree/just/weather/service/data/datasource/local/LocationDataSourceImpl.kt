package com.wing.tree.just.weather.service.data.datasource.local

import android.Manifest.permission
import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import com.wing.tree.just.weather.service.domain.exception.OnCanceledException
import com.wing.tree.just.weather.service.domain.model.local.location.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationDataSourceImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationDataSource {
    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    override fun latLng(): Flow<LatLng> = flow {
        val locationRequest = LocationRequest.create()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(context)

        locationSettingsResponse {
            settingsClient.checkLocationSettings(builder.build())
                .addOnCanceledListener {
                    throw OnCanceledException()
                }.addOnFailureListener { exception ->
                    throw exception
                }.addOnSuccessListener(it)
        }

        val latLng = latLng {
            fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                null
            ).addOnCanceledListener {
                throw OnCanceledException()
            }.addOnFailureListener { exception ->
                throw exception
            }.addOnSuccessListener(it)
        } ?: latLng {
            fusedLocationProviderClient.lastLocation
                .addOnCanceledListener {
                    throw OnCanceledException()
                }.addOnFailureListener { exception ->
                    throw exception
                }.addOnSuccessListener(it)
        }

        latLng?.let { emit(it) } ?: throw CancellationException()
        awaitCancellation()
    }

    private suspend fun locationSettingsResponse(
        block: (OnSuccessListener<LocationSettingsResponse>) -> Unit
    ): LocationSettingsResponse {
        return suspendCancellableCoroutine { cancellableContinuation ->
            block(
                OnSuccessListener {
                    cancellableContinuation.resume(it)
                }
            )
        }
    }

    private suspend fun latLng(block: (OnSuccessListener<Location>) -> Unit): LatLng? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            block(
                OnSuccessListener { location ->
                    location?.let {
                        cancellableContinuation.resume(
                            LatLng(
                                latitude = it.latitude,
                                longitude = it.longitude
                            )
                        )
                    } ?: cancellableContinuation.resume(null)
                }
            )
        }
    }
}