package com.rodolfogusson.testepag.infrastructure.data.repository

import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.*
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.infrastructure.service.dto.GenresResponse
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class GenresRepositoryTest {

    private lateinit var genresRepository: GenresRepository
    private val callMock = mock<Call<GenresResponse>>()
    private var serviceMock = mock<MoviesService> {
        on { getGenres(any(), any()) } doReturn callMock
    }


    private val jsonString =
        GenresRepositoryTest::class.java.getResource("/genres_response.json")?.readText()
    val genresResponse = Gson().fromJson(jsonString, GenresResponse::class.java)


    @Before
    fun setup() {
        genresRepository = GenresRepository(serviceMock)
    }

    @Test
    fun `when repository getGenres, it should getGenres from service with correct parameters`() {
        //GIVEN
        whenever(callMock.execute()).thenReturn(Response.success(genresResponse))

        //WHEN
        genresRepository.getGenres()

        //THEN
        verify(serviceMock).getGenres(MoviesService.apiKey, "pt-BR")
    }

    @Test
    fun `getGenres should return correct data`() {
        //GIVEN
        whenever(callMock.execute()).thenReturn(Response.success(genresResponse))

        //WHEN
        val response = genresRepository.getGenres()

        //THEN
        assertEquals(genresResponse.genres, response)
    }

    @Test(expected = Exception::class)
    fun `getGenres should throw an exception on failure`() {
        //GIVEN
        whenever(callMock.execute()).doThrow(Throwable())

        //WHEN
        genresRepository.getGenres()

        //THEN
        //expects exception
    }
}