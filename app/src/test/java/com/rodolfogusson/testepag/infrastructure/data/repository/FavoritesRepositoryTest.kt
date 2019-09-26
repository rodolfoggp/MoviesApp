package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.*
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.FavoriteDao
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.MovieGenreJoinDao
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.model.MovieGenreJoin
import kotlinx.coroutines.Dispatchers
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

    private val genresList = listOf(
        Genre(1, "A"),
        Genre(2, "B"),
        Genre(3, "C"),
        Genre(4, "D"),
        Genre(5, "E"),
        Genre(6, "F")
    )

    private fun configureMoviesList() {
        moviesList[0].genres = listOf(genresList[0], genresList[1])
        moviesList[1].genres = listOf(genresList[2], genresList[3])
        moviesList[2].genres = listOf(genresList[4], genresList[5])
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
        repository.add(movie)

        //THEN
        verify(favoriteDaoMock).insert(movie)
        verify(movieGenreJoinDaoMock).insert(argThat { equals(MovieGenreJoin(1, 10)) })
        verify(movieGenreJoinDaoMock).insert(argThat { equals(MovieGenreJoin(1, 20)) })
        verify(movieGenreJoinDaoMock).insert(argThat { equals(MovieGenreJoin(1, 30)) })
    }
}
