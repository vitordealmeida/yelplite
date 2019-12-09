package com.vb.yelplite.app.data

import com.vb.yelplite.app.domain.Business
import com.vb.yelplite.app.domain.BusinessDetails
import com.vb.yelplite.app.domain.BusinessReviewResponse
import org.koin.dsl.module

val dataModule = module {
    factory { BusinessRepository(get()) }
}

class BusinessRepository(private val api: YelpApi) {

    suspend fun searchBusinesses(latitude: Double, longitude: Double): List<Business> {
        return api.searchBusinesses(latitude, longitude).businesses
    }

    suspend fun getBusinessDetail(id: String): BusinessDetails {
        return api.getBusinessDetails(id)
    }

    suspend fun getBusinessReviews(id: String): BusinessReviewResponse {
        return api.getBusinessReviews(id)
    }
}