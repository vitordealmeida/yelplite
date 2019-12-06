package com.vb.yelplite.data

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

data class RemoteBusiness(
    val rating: Int,
    val price: String,
    val phone: String,
    val id: String,
    val categories: List<RemoteBusinessCategory>,
    val review_count: Int,
    val name: String,
    val url: String,
    val image_url: String,
    val location: RemoteBusinessLocation
)

data class RemoteBusinessLocation(
    val city: String,
    val country: String,
    val address1: String,
    val address2: String,
    val state: String,
    val zip_code: String
)

data class RemoteBusinessCategory(val alias: String, val title: String)

data class YelpBusinessSearchResponse(
    val businesses: List<RemoteBusiness>
)