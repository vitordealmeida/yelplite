package com.vb.yelplite.app.domain

import android.location.LocationManager
import io.reactivex.Observable

class GetCurrentLocation(val locationManager: LocationManager) {
    fun run(): Observable<Any?> {
        return Observable.create {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                it.onError(LocationProviderDisabledException())
                return@create
            }
        }
    }
}