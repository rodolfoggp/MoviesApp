package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.*
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.infrastructure.service.dto.MoviesResponse
import com.rodolfogusson.testepag.infrastructure.service.deserializer.LocalDateDeserializer
import com.rodolfogusson.testepag.infrastructure.service.dto.MoviesResponseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalCoroutinesApi
class MoviesRepositoryTest {

    private lateinit var repository: MoviesRepository

    private val callMock = mock<Call<MoviesResponse>>()

    val jsonString =
        MoviesResponseTest::class.java.getResource("/movies_response.json")?.readText()

    val builder = GsonBuilder().apply {
        registerTypeAdapter(
            LocalDate::class.java,
            LocalDateDeserializer()
        )
    }

    val moviesResponse = builder.create().fromJson(jsonString, MoviesResponse::class.java)

    val mockService = mock<MoviesService> {
        on { getMovies(any(), any(), any()) } doReturn callMock
    }

    @Before
    fun setup() {
        repository = MoviesRepository(mockService)
    }

    @Test
    fun `when repository getMovies, it should getMovies from service, with correct parameters`() =
        runBlockingTest {
            //GIVEN
            whenever(callMock.execute()).thenReturn(Response.success(moviesResponse))

            //WHEN
            repository.getMovies()


            //THEN
            verify(mockService).getMovies(MoviesService.apiKey, "pt-BR", 1)
        }

    @Test
    fun `getMovies should return the correct data`() = runBlockingTest {
        //GIVEN
        whenever(callMock.execute()).thenReturn(Response.success(moviesResponse))

        //WHEN
        val response = repository.getMovies()

        //THEN
        assertEquals(moviesResponse.results, response)
    }

    @Test(expected = Exception::class)
    fun `getMovies should thrown exception on failure`() = runBlockingTest {
        //GIVEN
        whenever(callMock.execute()).doThrow(Throwable())

        //WHEN
        repository.getMovies()

        //THEN
        //expects exception
    }
}