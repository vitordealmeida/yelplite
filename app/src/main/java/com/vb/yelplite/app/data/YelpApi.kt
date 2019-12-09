package com.vb.yelplite.app.data

import com.vb.yelplite.app.domain.BusinessDetails
import com.vb.yelplite.app.domain.BusinessReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpApi {
    @GET("businesses/search")
    suspend fun searchBusinesses(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): YelpBusinessSearchResponse

    @GET("businesses/{id}")
    suspend fun getBusinessDetails(@Path("id") id: String): BusinessDetails

    @GET("businesses/{id}/reviews")
    suspend fun getBusinessReviews(@Path("id") id: String): BusinessReviewResponse
}