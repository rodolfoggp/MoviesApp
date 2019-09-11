package com.rodolfogusson.testepag.model

import org.threeten.bp.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: LocalDate,
    val imageUrl: String,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: List<Genre>
)