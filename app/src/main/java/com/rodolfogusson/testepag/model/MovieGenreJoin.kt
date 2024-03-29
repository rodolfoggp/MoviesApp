package com.rodolfogusson.testepag.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

/**
 * Class that represents a many-to-many relationship
 * between Movie and Genre in the database.
 */
@Entity(
    tableName = "MovieGenreJoin",
    primaryKeys = ["movieId", "genreId"],
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movieId"),
            onDelete = CASCADE
        ), ForeignKey(
            entity = Genre::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("genreId")
        )
    ]
)
data class MovieGenreJoin(
    val movieId: Int,
    val genreId: Int
)