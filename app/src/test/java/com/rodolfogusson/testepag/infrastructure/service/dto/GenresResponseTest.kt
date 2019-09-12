package com.rodolfogusson.testepag.infrastructure.service.dto

import com.google.gson.Gson
import com.rodolfogusson.testepag.model.Genre
import org.junit.Assert.assertEquals
import org.junit.Test

class GenresResponseTest {
    @Test
    fun `GenresResponse is deserialized correctly`() {
        //GIVEN
        val json = """
            {  
               "genres":[  
                  {  
                     "id":28,
                     "name":"Ação"
                  },
                  {  
                     "id":12,
                     "name":"Aventura"
                  },
                  {  
                     "id":16,
                     "name":"Animação"
                  }
               ]
            }
        """.trimIndent()

        //WHEN
        val response = Gson().fromJson(json, GenresResponse::class.java)

        //THEN
        assertEquals(Genre(28, "Ação"), response.genres[0])
        assertEquals(Genre(12, "Aventura"), response.genres[1])
        assertEquals(Genre(16, "Animação"), response.genres[2])
    }
}