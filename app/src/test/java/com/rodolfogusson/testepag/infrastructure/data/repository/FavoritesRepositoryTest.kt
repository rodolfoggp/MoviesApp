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

class FavoritesRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: FavoritesRepository

    private val getFavoriteByIdLiveData = MutableLiveData<Movie>()
    private val getAllFavoritesLiveData = MutableLiveData<List<Movie>>()

    private val testHelper = TestHelper()

    private val moviesList = TestHelper().moviesList
    private val moviesWithGenresList =
        TestHelper().moviesList.apply { forEach { it.genres = testHelper.genresFor(it.id) } }

    private val movie = moviesList[0]
    private val movieWithGenres =
        TestHelper().moviesList[0].apply { genres = testHelper.genresFor(moviesList[0].id) }

    private val id = movie.id

    private val favoriteDaoMock: FavoriteDao = mock {
        on { getFavoriteById(id) } doReturn getFavoriteByIdLiveData
        on { getAllFavorites() } doReturn getAllFavoritesLiveData
    }

    private val movieGenreJoinDaoMock: MovieGenreJoinDao = mock()
    fun configureMovieGenreJoinDao() {
        for (movie in moviesList) {
            whenever(movieGenreJoinDaoMock.getGenresForMovie(movie.id))
                .thenReturn(testHelper.genresFor(movie.id))
        }
    }

    @Before
    fun setup() {
        configureMovieGenreJoinDao()
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
        getAllFavoritesLiveData.value = moviesList

        //WHEN
        val result = repository.getFavorites()

        //THEN
        verify(favoriteDaoMock).getAllFavorites()
        val favorites = result
            .test()
            .awaitValue()
            .value()
        assertEquals(moviesWithGenresList, favorites)
    }

    @Test
    fun `getFavoriteById should get the correct favorite with its genres`() {
        //GIVEN
        getFavoriteByIdLiveData.value = movie

        //WHEN
        val result = repository.getFavoriteById(id)

        //THEN
        verify(favoriteDaoMock).getFavoriteById(id)
        val favorite = result
            .test()
            .awaitValue()
            .value()
        assertEquals(movieWithGenres, favorite)
    }

    @Test
    fun `isFavorite should getFavoriteById and return true if it exists`() {
        //GIVEN
        getFavoriteByIdLiveData.value = movie

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
        getFavoriteByIdLiveData.value = null

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
        val genres = movieWithGenres.genres ?: emptyList()
        assert(genres.isNotEmpty())

        //WHEN
        repository.add(movieWithGenres)

        //THEN
        verify(favoriteDaoMock).insert(movieWithGenres)
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
