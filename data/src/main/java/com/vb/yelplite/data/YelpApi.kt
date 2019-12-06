package com.vb.yelplite.data

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface YelpApi {
    @GET("businesses/search")
    fun searchBusinesses(): Deferred<Response<YelpBusinessSearchResponse>>

    @GET("businesses/{id}")
    fun getBusinessDetails(): Deferred<Response<YelpBusinessSearchResponse>>
}