package com.vb.yelplite.app.domain

import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class GetCurrentLocation(
    val locationManager: LocationManager,
    val fusedLocationProviderClient: FusedLocationProviderClient
) {
    var bestLocation: Location? = null
    var bestAccuracy = Float.MAX_VALUE

    suspend fun run(): Location {
        if (!isLocationEnabled()) {
            throw LocationProviderDisabledException()
        }
        val location = fusedLocationProviderClient.lastLocation.await()

        if (location != null) {
            bestLocation = location
            bestAccuracy = location.accuracy
            return location
        }

        // phone has been restarted, must request location update
        val locationRequest = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 500
            fastestInterval = 100
            numUpdates = 10
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location2 = locationResult.locations.sortedBy { it.accuracy }[0]
                    bestLocation = location2
                    bestAccuracy = location2.accuracy
                }
            },
            Looper.myLooper()
        ).await()

        var awaitedTime = 0
        while (awaitedTime < 1500 && bestAccuracy > 200) {
            delay(200)
            awaitedTime += 200
        }

        return bestLocation ?: Location("")
    }

    private fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}