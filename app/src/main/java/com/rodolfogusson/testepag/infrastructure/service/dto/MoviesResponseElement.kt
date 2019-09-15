package com.rodolfogusson.testepag.infrastructure.service.dto

import com.google.gson.annotations.SerializedName
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie
import org.threeten.bp.LocalDate

data class MoviesResponseElement(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("release_date") val releaseDate: LocalDate,
    @SerializedName("poster_path") val imageUrl: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("genre_ids") val genreIds: List<Int>
) {
    fun toMovie(allGenres: List<Genre>): Movie {
        val genres = allGenres.filter { genre -> genreIds.contains(genre.id) }
        return Movie(
            id,
            title,
            overview,
            releaseDate,
            imageUrl,
            voteAverage,
            voteCount,
            genres
        )
    }
}