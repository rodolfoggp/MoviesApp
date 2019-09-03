package com.rodolfogusson.testepag.model

data class Movie(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val imageUrl: String,
    val voteAverage: Double,
    val voteCount: Int,
    val overview: String
)