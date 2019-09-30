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

    override fun equals(other: Any?): Boolean {
        if (other !is Movie) return false
        //return super.equals(other) && this.genres == other.genres
        return hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + voteCount
        result = 31 * result + (genres?.hashCode() ?: 0)
        return result
    }
}