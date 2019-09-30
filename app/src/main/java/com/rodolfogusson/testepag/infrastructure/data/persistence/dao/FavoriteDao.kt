package com.rodolfogusson.testepag.infrastructure.data.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rodolfogusson.testepag.model.Movie

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM Movie")
    fun getAllFavorites(): LiveData<List<Movie>>

    @Query("SELECT * FROM Movie WHERE Movie.id=:id")
    fun getFavoriteById(id: Int): LiveData<Movie>

    @Insert(onConflict = REPLACE)
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)
}