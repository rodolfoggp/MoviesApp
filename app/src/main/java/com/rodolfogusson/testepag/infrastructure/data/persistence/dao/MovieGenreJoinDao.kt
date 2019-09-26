package com.rodolfogusson.testepag.infrastructure.data.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.MovieGenreJoin

@Dao
interface MovieGenreJoinDao {
    @Insert(onConflict = REPLACE)
    fun insert(movieGenreJoin: MovieGenreJoin)

    @Query("""SELECT * FROM Genre 
        INNER JOIN MovieGenreJoin ON Genre.id=MovieGenreJoin.genreId 
        WHERE MovieGenreJoin.movieId=:movieId
    """)
    fun getGenresForMovie(movieId: Int): List<Genre>
}