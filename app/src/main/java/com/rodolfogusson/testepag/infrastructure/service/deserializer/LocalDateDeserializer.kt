package com.rodolfogusson.testepag.infrastructure.service.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.threeten.bp.LocalDate
import java.lang.Exception
import java.lang.reflect.Type

class LocalDateDeserializer : JsonDeserializer<LocalDate> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        if (json == null) throw Exception()
        val string = json.asString
        return LocalDate.parse(string)
    }
}