package com.rodolfogusson.testepag.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("release_date") val releaseDate: LocalDate,
    @SerializedName("poster_path") val imageUrl: String,
    @SerializedName("vote_average")val voteAverage: Double,
    @SerializedName("vote_count")val voteCount: Int,
    val overview: String
)