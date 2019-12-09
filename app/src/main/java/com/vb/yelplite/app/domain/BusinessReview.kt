package com.vb.yelplite.app.domain

data class BusinessReviewResponse(val reviews: List<BusinessReview>)
data class BusinessReview(
    val id: String,
    val rating: Int,
    val user: BusinessReviewUser,
    val text: String,
    val url: String
)

data class BusinessReviewUser(
    val id: String,
    val profile_url: String,
    val image_url: String,
    val name: String
)
