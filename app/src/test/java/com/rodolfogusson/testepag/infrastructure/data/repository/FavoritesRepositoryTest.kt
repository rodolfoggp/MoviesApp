package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.*
import com.rodolfogusson.testepag.TestHelper
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.FavoriteDao
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.MovieGenreJoinDao
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.model.MovieGenreJoin
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

class FavoritesRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: FavoritesRepository

    private val favoriteLiveData = MutableLiveData<Movie>()
    private val favoriteListLiveData = MutableLiveData<List<Movie>>()
    private val testHelper = TestHelper()

    private val moviesList = testHelper.moviesList
    private val movie = moviesList[0]
    private val id = movie.id

    private val favoriteDaoMock: FavoriteDao = mock {
        on { getFavoriteById(id) } doReturn favoriteLiveData
        on { getAllFavorites() } doReturn favoriteListLiveData
    }

    private val movieGenreJoinDaoMock: MovieGenreJoinDao = mock()

    @Before
    fun setup() {
        testHelper.includeGenresInMovies()
        repository = FavoritesRepository.getInstance(
            favoriteDaoMock,
            movieGenreJoinDaoMock,
            Dispatchers.Unconfined
        )
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
    fun `getFavorites should return all the favorites with their genres`() {
        //GIVEN
        favoriteListLiveData.value = moviesList
        for (movie in moviesList) {
            whenever(movieGenreJoinDaoMock.getGenresForMovie(movie.id)).thenReturn(movie.genres)
        }

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
    fun `getFavoriteById should get the correct favorite`() {
        //GIVEN
        favoriteLiveData.value = movie

        //WHEN
        val liveData = repository.getFavoriteById(id)

        //THEN
        verify(favoriteDaoMock).getFavoriteById(id)
        val favorite = liveData
            .test()
            .awaitValue()
            .value()
        assertEquals(movie, favorite)
    }


    @Test
    fun `isFavorite should getFavoriteById and return true if it exists`() {
        //GIVEN
        favoriteLiveData.value = movie

        //WHEN
        val result = repository.isFavorite(movie.id)

        //THEN
        verify(favoriteDaoMock).getFavoriteById(movie.id)
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
        val result = repository.isFavorite(id)

        //THEN
        verify(favoriteDaoMock).getFavoriteById(id)
        val isFavorite = result.test()
            .awaitValue()
            .value()
        assertEquals(false, isFavorite)
    }

    @Test
    fun `add should save a movie to the database and save all related moviegenrejoins`() {
        //GIVEN
        val genres = movie.genres ?: emptyList()
        assert(genres.isNotEmpty())

        //WHEN
        repository.add(movie)

        //THEN
        verify(favoriteDaoMock).insert(movie)
        for (genre in genres) {
            verify(movieGenreJoinDaoMock).insert(argThat {
                equals(
                    MovieGenreJoin(
                        movie.id,
                        genre.id
                    )
                )
            })
        }
    }
}
