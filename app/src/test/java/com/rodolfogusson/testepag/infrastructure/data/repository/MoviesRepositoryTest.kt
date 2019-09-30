package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.*
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.data.Status
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.infrastructure.service.deserializer.LocalDateDeserializer
import com.rodolfogusson.testepag.infrastructure.service.dto.GenresResponse
import com.rodolfogusson.testepag.infrastructure.service.dto.MoviesResponse
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepositoryTest {

    private lateinit var repository: MoviesRepository

    private val builder = GsonBuilder().apply {
        registerTypeAdapter(
            LocalDate::class.java,
            LocalDateDeserializer()
        )
    }
    private val moviesJson = MoviesRepositoryTest::class.java
        .getResource("/movies_response.json")?.readText()
    private val moviesResponse = builder.create().fromJson(moviesJson, MoviesResponse::class.java)

    private val genresJson =
        MoviesRepositoryTest::class.java.getResource("/genres_response.json")?.readText()
    private val genresResponse = Gson().fromJson(genresJson, GenresResponse::class.java)

    private val callMock = mock<Call<MoviesResponse>>()
    private val serviceMock = mock<MoviesService> {
        on { getMovies(any(), any(), any()) } doReturn callMock
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = MoviesRepository.getInstance(serviceMock)
    }

    @After
    fun tearDown() {
        // Used to reset singleton instance
        val field = MoviesRepository::class.java.getDeclaredField("instance")
        field.isAccessible = true
        field.set(null, null)
    }

    @Test
    fun `MoviesRepository should be a singleton`() {
        val repo1 = MoviesRepository.getInstance(serviceMock)
        val repo2 = MoviesRepository.getInstance(serviceMock)
        assert(repo1 === repo2)
    }

    @Test
    fun `movies request should getMovies from service with correct parameters`() {
        //WHEN
        repository.getMovies(listOf(), 1)

        //THEN
        verify(serviceMock).getMovies(MoviesService.apiKey, "pt-BR", 1)
    }

    @Test
    fun `movies request should return the correct data`() {
        //GIVEN
        doAnswer {
            val callback: Callback<MoviesResponse> = it.getArgument(0)
            callback.onResponse(callMock, Response.success(moviesResponse))
        }.whenever(callMock).enqueue(any())

        val movies = moviesResponse.results.map { e -> e.toMovie(genresResponse.genres) }
        val expectedResource = Resource.success(movies)

        //WHEN
        val liveData = repository.getMovies(genresResponse.genres, 1)

        //THEN
        val moviesResource = liveData
            .test()
            .value()
        assertEquals(expectedResource.data, moviesResource.data)
    }

    @Test
    fun `movies request should return an error when it fails`() {
        //GIVEN
        doAnswer {
            val callback: Callback<MoviesResponse> = it.getArgument(0)
            callback.onFailure(callMock, Throwable())
        }.whenever(callMock).enqueue(any())

        //WHEN
        val liveData = repository.getMovies(listOf(), 1)

        //THEN
        val resource = liveData
            .test()
            .value()
        assertEquals(Status.ERROR, resource.status)
    }

    @Test
    fun `getMovieById should get the correct movie`() {
        //GIVEN
        doAnswer {
            val callback: Callback<MoviesResponse> = it.getArgument(0)
            callback.onResponse(callMock, Response.success(moviesResponse))
        }.whenever(callMock).enqueue(any())

        repository.getMovies(genresResponse.genres, 1)

        //We are looking for this random movie
        val movieWanted = moviesResponse.results[13].toMovie(genresResponse.genres)

        //WHEN
        val liveData = repository.getMovieById(movieWanted.id)

        //THEN
        val movie = liveData
            .test()
            .awaitValue()
            .value()

        assertEquals(movieWanted, movie)
    }
}
