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

    private val moviesList = listOf(
        Movie(
            1,
            "Filme 1",
            "Descrição 1",
            LocalDate.parse("2019-05-12"),
            "/wF6SNPcUrTKFA4fOFfukm7zQ3ob.jpg",
            6.4,
            10
        ),
        Movie(
            2,
            "Filme 2",
            "Descrição 2",
            LocalDate.parse("2019-08-26"),
            "/foEOVg4HQl2VzKzTh27CAHmXyg.jpg",
            7.9,
            17
        ),
        Movie(
            3,
            "Filme 3",
            "Descrição 2",
            LocalDate.parse("2019-07-22"),
            "/foEOVg4HQl2VzKzTh27CAHmXyg.jpg",
            8.0,
            19
        )
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
        val genresForThisMovie = listOf(genres[2], genres[3])
        val movie = moviesList[0].apply { genres = genresForThisMovie }
        favoriteDao.insert(movie)
        genreDao.insertAll(genres)

        //WHEN
        for (genre in movie.genres!!) {
            movieGenreJoinDao.insert(MovieGenreJoin(movie.id, genre.id))
        }

        //AND
        /*val genresLiveData = movieGenreJoinDao.getGenresForMovie(movie.id)
        genresLiveData.observeForever {  }

        val genres = genresLiveData.value*/

        val genres = movieGenreJoinDao.getGenresForMovie(movie.id)

        //THEN
        assertEquals(genresForThisMovie, genres)
    }

    @Test
    fun whenMovieIsDeleted_AssociatedMovieGenreJoinsAreDeletedOnCascade() {
        //GIVEN
        genreDao.insertAll(genres)
        val genresForMovie1 = listOf(genres[2], genres[3])
        val movie1 = moviesList[0].apply { genres = genresForMovie1 }
        favoriteDao.insert(movie1)
        for (genre in movie1.genres!!) {
            movieGenreJoinDao.insert(MovieGenreJoin(movie1.id, genre.id))
        }

        /*val genresLiveData = movieGenreJoinDao.getGenresForMovie(movie1.id)
        genresLiveData.observeForever {  }

        val genresBeforeDeletion = genresLiveData.value*/
        val genresBeforeDeletion = movieGenreJoinDao.getGenresForMovie(movie1.id)
        assertEquals(genresForMovie1, genresBeforeDeletion)

        //WHEN
        favoriteDao.delete(movie1)

        //THEN
        //val genresAfterDeletion = genresLiveData.value
        val genresAfterDeletion = movieGenreJoinDao.getGenresForMovie(movie1.id)
        assertEquals(emptyList<Genre>(), genresAfterDeletion)
    }
}