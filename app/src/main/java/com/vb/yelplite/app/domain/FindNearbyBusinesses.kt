package com.vb.yelplite.app.domain

import com.vb.yelplite.app.data.BusinessRepository

class FindNearbyBusinesses(val getCurrentLocation: GetCurrentLocation, val businessRepository: BusinessRepository) {
    suspend fun run(): List<Business> {
        val location = getCurrentLocation.run()
        return businessRepository.searchBusinesses(location.latitude, location.longitude) ?: emptyList()
    }
}