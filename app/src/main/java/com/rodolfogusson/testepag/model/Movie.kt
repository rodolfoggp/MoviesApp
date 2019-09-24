package com.rodolfogusson.testepag.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: LocalDate,
    val imageUrl: String?,
    val voteAverage: Double,
    val voteCount: Int
) {
    @Ignore var genres: List<Genre>? = null
}