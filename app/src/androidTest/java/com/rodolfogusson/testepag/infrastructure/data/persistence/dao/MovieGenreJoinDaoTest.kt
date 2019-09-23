package com.rodolfogusson.testepag.infrastructure.data.persistence.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rodolfogusson.testepag.infrastructure.data.persistence.database.ApplicationDatabase
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.model.MovieGenreJoin
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieGenreJoinDaoTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var db: ApplicationDatabase
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var genreDao: GenreDao
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var movieGenreJoinDao: MovieGenreJoinDao

    private val movie = Movie(
        1,
        "Filme 1",
        "Descrição 1",
        LocalDate.parse("2019-05-12"),
        "/wF6SNPcUrTKFA4fOFfukm7zQ3ob.jpg",
        6.4,
        10
    )

    private val genres: List<Genre> = listOf(
        Genre(1, "Aventura"),
        Genre(2, "Comédia"),
        Genre(3, "Ação"),
        Genre(4, "Drama"),
        Genre(5, "Suspense")
        )

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase::class.java).build()
        genreDao = db.genreDao()
        favoriteDao = db.favoriteDao()
        movieGenreJoinDao = db.movieGenreJoinDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun daoShouldInsertAndGetGenresForMovieCorrectly() {
        //GIVEN
        favoriteDao.insert(movie)
        genreDao.insertAll(genres)
        val genresForThisMovie = listOf(genres[2], genres[3])
        val movie = movie.apply { genres = genresForThisMovie }

        //WHEN
        for (genre in movie.genres!!) {
            movieGenreJoinDao.insert(MovieGenreJoin(movie.id, genre.id))
        }

        //AND
        val genresLiveData = movieGenreJoinDao.getGenresForMovie(movie.id)
        genresLiveData.observeForever {  }

        val genres = genresLiveData.value

        //THEN
        assertEquals(genresForThisMovie, genres)
    }
}