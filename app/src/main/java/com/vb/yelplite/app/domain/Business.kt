package com.vb.yelplite.app.domain

data class Business(
    val rating: Float,
    val price: String?,
    val phone: String,
    val id: String,
    val categories: List<BusinessCategory>,
    val review_count: Int,
    val name: String,
    val url: String,
    val image_url: String,
    val location: BusinessLocation
)

data class BusinessLocation(
    val city: String,
    val country: String,
    val address1: String,
    val address2: String,
    val state: String,
    val zip_code: String
) {
    override fun toString(): String {
        return "$address1, $city, $state"
    }
}

data class BusinessCategory(val alias: String, val title: String)
