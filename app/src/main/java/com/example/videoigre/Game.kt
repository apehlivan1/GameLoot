package com.example.videoigre

import java.io.Serializable

data class Game(
    val title: String,
    val platform: String,
    val releaseDate: String,
    val rating: Double,
    val coverImage: String,
    val esrbRating: String,
    val developer: String,
    val publisher: String,
    val genre: String,
    val description: String,
    val userImpressions: List<UserImpression>,
    ) : Serializable {
    init {
        if (userImpressions.isNotEmpty()) {
            userImpressions.sortedByDescending { it.timestamp }
        }
    }
}
