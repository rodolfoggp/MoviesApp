package com.rodolfogusson.testepag.infrastructure.data.persistence.database

import android.content.Context
import androidx.room.*
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.FavoriteDao
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.GenreDao
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.MovieGenreJoinDao
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.model.MovieGenreJoin

@Database(
    entities = [Movie::class, Genre::class, MovieGenreJoin::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun genreDao(): GenreDao
    abstract fun movieGenreJoinDao(): MovieGenreJoinDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: ApplicationDatabase? = null

        private const val databaseName = "database.db"

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    databaseName
                ).build().also { instance = it }
            }
    }
}