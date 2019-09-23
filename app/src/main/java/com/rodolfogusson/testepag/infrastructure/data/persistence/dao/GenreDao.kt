package com.rodolfogusson.testepag.infrastructure.data.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rodolfogusson.testepag.model.Genre

@Dao
interface GenreDao {
    @Insert(onConflict = REPLACE)
    fun insert(genre: Genre)

    @Query("SELECT * FROM Genre WHERE id=:id")
    fun getGenreById(id: Int): LiveData<Genre>
}