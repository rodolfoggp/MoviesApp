package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.FavoriteDao
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.MovieGenreJoinDao
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.model.MovieGenreJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate

class FavoritesRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: FavoritesRepository

    private val mockId = 1
    private val favoriteLiveData = MutableLiveData<Movie>()
    private val favoriteListLiveData = MutableLiveData<List<Movie>>()

    private val favoriteDaoMock: FavoriteDao = mock {
        on { getFavoriteById(mockId) } doReturn favoriteLiveData
        on { getAllFavorites() } doReturn favoriteListLiveData
    }

    private val movieGenreJoinDaoMock: MovieGenreJoinDao = mock()

    private fun configureMoviesList() {
        for (movie in moviesList) movie.genres = listOf()
    }

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

    @Before
    fun setup() {
        configureMoviesList()
        repository = FavoritesRepository.getInstance(favoriteDaoMock, movieGenreJoinDaoMock)
    }

    @After
    fun tearDown() {
        // Used to reset singleton instance
        val field = FavoritesRepository::class.java.getDeclaredField("instance")
        field.isAccessible = true
        field.set(null, null)
    }

    @Test
    fun `FavoritesRepository should be a singleton`() {
        val repo1 = FavoritesRepository.getInstance(favoriteDaoMock, movieGenreJoinDaoMock)
        val repo2 = FavoritesRepository.getInstance(favoriteDaoMock, movieGenreJoinDaoMock)
        assert(repo1 === repo2)
    }

    @Test
    fun `getFavorites should return all the favorites`() {
        //GIVEN
        favoriteListLiveData.value = moviesList

        //WHEN
        val result = repository.getFavorites()

        //THEN
        verify(favoriteDaoMock).getAllFavorites()
        val favorites = result
            .test()
            .awaitValue()
            .value()
        assertEquals(moviesList, favorites)
    }

    @Test
    fun `isFavorite should getFavoriteById and return true if it exists`() {
        //GIVEN
        favoriteLiveData.value = moviesList[0]

        //WHEN
        val result = repository.isFavorite(mockId)

        //THEN
        verify(favoriteDaoMock).getFavoriteById(mockId)
        val isFavorite = result.test()
            .awaitValue()
            .value()
        assertEquals(true, isFavorite)
    }

    @Test
    fun `isFavorite should getFavoriteById and return false if it doesn't exist`() {
        //GIVEN
        favoriteLiveData.value = null

        //WHEN
        val result = repository.isFavorite(mockId)

        //THEN
        verify(favoriteDaoMock).getFavoriteById(mockId)
        val isFavorite = result.test()
            .awaitValue()
            .value()
        assertEquals(false, isFavorite)
    }

    @Test
    fun `add should save a movie to the database and save all related moviegenrejoins`() {
        //GIVEN
        val genres = listOf(Genre(10, "A"), Genre(20, "B"), Genre(30, "C"))
        val movie = moviesList[0]
        movie.genres = genres

        //WHEN
        runBlocking { repository.add(movie) }

        //THEN
        verify(favoriteDaoMock).insert(movie)
        verify(movieGenreJoinDaoMock).insert(argThat { equals(MovieGenreJoin(1, 10)) })
        verify(movieGenreJoinDaoMock).insert(argThat { equals(MovieGenreJoin(1, 20)) })
        verify(movieGenreJoinDaoMock).insert(argThat { equals(MovieGenreJoin(1, 30)) })
    }

    @Test
    fun `remove should remove a movie from the database and all related moviegenrejoins`() {
            //GIVEN
            val genres = listOf(Genre(10, "A"), Genre(20, "B"), Genre(30, "C"))
            val movie = moviesList[0]
            movie.genres = genres
            runBlocking { repository.add(movie) }

            //WHEN
            runBlocking { repository.remove(movie) }

            //THEN
            verify(favoriteDaoMock).delete(movie)
            verify(movieGenreJoinDaoMock).deleteAllGenresForMovie(movie.id)
        }
}
