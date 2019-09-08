package com.rodolfogusson.testepag.infrastructure.service.dto

import com.google.gson.GsonBuilder
import com.rodolfogusson.testepag.infrastructure.service.deserializer.LocalDateDeserializer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate

class MoviesResponseTest {

    @Test
    fun `MoviesResponseTest is correctly deserialized`() {
        //GIVEN
        val jsonString =
            MoviesResponseTest::class.java.getResource("/movies_response.json")?.readText()

        //WHEN
        val builder = GsonBuilder().apply { registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer()) }
        val response = builder.create().fromJson(jsonString, MoviesResponse::class.java)

        //THEN
        assertEquals(1, response.page)
        assertEquals(20, response.results.size)
        assertEquals(15, response.totalPages)
    }
}