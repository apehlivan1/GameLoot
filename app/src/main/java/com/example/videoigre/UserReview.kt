package com.example.videoigre

data class UserReview(
    override val username: String,
    override val timestamp: Long,
    val review: String
):UserImpression()