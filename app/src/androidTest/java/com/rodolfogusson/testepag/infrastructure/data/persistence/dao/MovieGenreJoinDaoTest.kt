package com.rodolfogusson.testepag.infrastructure.data.persistence.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rodolfogusson.testepag.TestHelper
import com.rodolfogusson.testepag.infrastructure.data.persistence.database.ApplicationDatabase
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.MovieGenreJoin
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
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

    private val testHelper = TestHelper()
    private val moviesList = testHelper.moviesList
    private val genres = testHelper.genresList

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
        val genresForThisMovie = listOf(genres[2], genres[3])
        val movie = moviesList[0].apply { genres = genresForThisMovie }
        favoriteDao.insert(movie)
        genreDao.insertAll(genres)

        //WHEN
        for (genre in movie.genres!!) {
            movieGenreJoinDao.insert(MovieGenreJoin(movie.id, genre.id))
        }

        //AND
        val genres = movieGenreJoinDao.getGenresForMovie(movie.id)

        //THEN
        assertEquals(genresForThisMovie, genres)
    }

    @Test
    fun whenMovieIsDeleted_AssociatedMovieGenreJoinsAreDeletedOnCascade() {
        //GIVEN
        genreDao.insertAll(genres)

        val genresForMovie = listOf(genres[2], genres[3])
        val movie = moviesList[0].apply { genres = genresForMovie }

        favoriteDao.insert(movie)
        for (genre in movie.genres!!) {
            movieGenreJoinDao.insert(MovieGenreJoin(movie.id, genre.id))
        }

        val genresBeforeDeletion = movieGenreJoinDao.getGenresForMovie(movie.id)
        assertEquals(genresForMovie, genresBeforeDeletion)

        //WHEN
        favoriteDao.delete(movie)

        //THEN
        val genresAfterDeletion = movieGenreJoinDao.getGenresForMovie(movie.id)
        assertEquals(emptyList<Genre>(), genresAfterDeletion)
    }
}