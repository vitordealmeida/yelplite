package com.vb.yelplite.app.domain

data class BusinessDetails(
    val photos: List<String>,
    val rating: Float,
    val price: String?,
    val phone: String,
    val id: String,
    val categories: List<BusinessCategory>,
    val review_count: Int,
    val name: String,
    val url: String,
    val image_url: String,
    val location: BusinessDetailsLocation,
    val isClosed: Boolean,
    val display_phone: String,
    val hours: List<BusinessDetailsHour>
)

data class BusinessDetailsLocation(
    val city: String,
    val country: String,
    val address1: String,
    val address2: String,
    val state: String,
    val zip_code: String,
    val display_address: List<String>
) {
    override fun toString(): String {
        return display_address.reduceRight { s, acc -> "$s, $acc" }
    }
}

data class BusinessDetailsHour(
    val open: List<BusinessDetailsOpenHour>
)

data class BusinessDetailsOpenHour(
    val isOvernight: Boolean,
    val start: String,
    val end: String,
    val day: Int
)