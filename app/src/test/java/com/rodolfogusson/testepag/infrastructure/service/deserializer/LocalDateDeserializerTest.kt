package com.rodolfogusson.testepag.infrastructure.service.deserializer

import com.google.gson.GsonBuilder
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate

class LocalDateDeserializerTest {

    data class ClassContainingLocalDate(val date: LocalDate)

    @Test
    fun `LocalDateDeserializer works correctly`() {
        //GIVEN
        val json = """{ "date": "2019-08-01" }"""
        val builder = GsonBuilder().apply { registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer()) }

        //WHEN
        val myObject = builder.create().fromJson(json, ClassContainingLocalDate::class.java)

        //THEN
        assertEquals(myObject.date, LocalDate.parse("2019-08-01"))
    }
}