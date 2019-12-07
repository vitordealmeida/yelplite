package com.vb.yelplite.app.domain

import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.GlobalScope

class GetCurrentLocation(
    val locationManager: LocationManager,
    val fusedLocationProviderClient: FusedLocationProviderClient
) {
    suspend fun run(): Location {
        if (!isLocationEnabled()) {
            throw LocationProviderDisabledException()
        }
        val de = fusedLocationProviderClient.lastLocation

//            var location: Location? = task.result
//            if (location == null) {
//                var mLocationRequest = LocationRequest()
//                mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//                mLocationRequest.interval = 0
//                mLocationRequest.fastestInterval = 0
//                mLocationRequest.numUpdates = 1
//
//                fusedLocationProviderClient.requestLocationUpdates(
//                    mLocationRequest, object : LocationCallback() {
//                        override fun onLocationResult(locationResult: LocationResult) {
//                            it.onNext(locationResult.lastLocation)
//                        }
//                    },
//                    Looper.myLooper()
//                )
//            } else {
//                return location
//            }
        return Location("")
    }

    private fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}