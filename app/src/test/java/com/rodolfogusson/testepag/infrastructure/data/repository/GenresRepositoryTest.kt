package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.*
import com.rodolfogusson.testepag.infrastructure.data.Status
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.infrastructure.service.dto.GenresResponse
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenresRepositoryTest {

    private lateinit var genresRepository: GenresRepository

    private val jsonString =
        GenresRepositoryTest::class.java.getResource("/genres_response.json")?.readText()
    private val genresResponse = Gson().fromJson(jsonString, GenresResponse::class.java)

    private val callMock = mock<Call<GenresResponse>>()
    private var serviceMock = mock<MoviesService> {
        on { getGenres(any(), any()) } doReturn callMock
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        genresRepository = GenresRepository.getInstance(serviceMock)
    }

    @After
    fun tearDown() {
        // Used to reset singleton instance
        val field = GenresRepository::class.java.getDeclaredField("instance")
        field.isAccessible = true
        field.set(null, null)
    }

    @Test
    fun `GenresRepository should be a singleton`() {
        val repo1 = GenresRepository.getInstance(serviceMock)
        val repo2 = GenresRepository.getInstance(serviceMock)
        assert(repo1 === repo2)
    }

    @Test
    fun `genres request should getGenres from service with correct parameters`() {
        //GIVEN
        doAnswer {
            val callback: Callback<GenresResponse> = it.getArgument(0)
            callback.onResponse(callMock, Response.success(genresResponse))
        }.whenever(callMock).enqueue(any())

        //WHEN
        genresRepository.getGenres()

        //THEN
        verify(serviceMock).getGenres(MoviesService.apiKey, "pt-BR")
    }

    @Test
    fun `genres request should return the correct data`() {
        //GIVEN
        doAnswer {
            val callback: Callback<GenresResponse> = it.getArgument(0)
            callback.onResponse(callMock, Response.success(genresResponse))
        }.whenever(callMock).enqueue(any())

        //WHEN
        val liveData = genresRepository.getGenres()

        //THEN
        val resource = liveData
            .test()
            .value()
        assertEquals(genresResponse.genres, resource.data)
    }

    @Test
    fun `genres request  should throw an exception on failure`() {
        //GIVEN
        doAnswer {
            val callback: Callback<GenresResponse> = it.getArgument(0)
            callback.onFailure(callMock, Throwable())
        }.whenever(callMock).enqueue(any())

        //WHEN
        val liveData = genresRepository.getGenres()

        //THEN
        val resource = liveData
            .test()
            .value()
        assertEquals(Status.ERROR, resource.status)
    }
}
