package com.vb.yelplite.app.data

import retrofit2.http.GET
import retrofit2.http.Query

interface YelpApi {
    @GET("businesses/search")
    suspend fun searchBusinessesTerm(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double, @Query("term") term: String): YelpBusinessSearchResponse

    @GET("businesses/search")
    suspend fun searchBusinesses(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): YelpBusinessSearchResponse

    @GET("businesses/{id}")
    suspend fun getBusinessDetails(): YelpBusinessSearchResponse
}