package com.rodolfogusson.testepag.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rodolfogusson.testepag.infrastructure.service.deserializer.LocalDateDeserializer
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate

class MovieTest {

    @Test
    fun `Movie is deserialized correctly`() {
        //GIVEN
        val movieJson = """
            {  
         "popularity":36.485,
         "id":454640,
         "video":false,
         "vote_count":99,
         "vote_average":6,
         "title":"Angry Birds 2: O Filme",
         "release_date":"2019-10-03",
         "original_language":"en",
         "original_title":"The Angry Birds Movie 2",
         "genre_ids":[  
            16,
            35,
            28,
            12,
            10751
         ],
         "backdrop_path":"\/k7sE3loFwuU2mqf7FbZBeE3rjBa.jpg",
         "adult":false,
         "overview":"As novas aventuras dos pássaros mais mal humorados do planeta. Depois de descobrirem os mistérios por trás da chegada dos porcos na ilha em que viviam, Red, Chuck e Bomb se juntam em novas confusões, cada vez mais irritados.",
         "poster_path":"\/ebe8hJRCwdflNQbUjRrfmqtUiNi.jpg"
        }
        """
        val builder = GsonBuilder().apply { registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer()) }

        //WHEN
        val movie = builder.create().fromJson(movieJson, Movie::class.java)

        //THEN
        assertEquals(454640, movie.id)
        assertEquals("Angry Birds 2: O Filme", movie.title)
        assertEquals(6.0, movie.voteAverage)
        assertEquals(99, movie.voteCount)
        assertEquals("/ebe8hJRCwdflNQbUjRrfmqtUiNi.jpg", movie.imageUrl)
        assertEquals("As novas aventuras dos pássaros mais mal humorados do planeta. Depois de descobrirem os mistérios por trás da chegada dos porcos na ilha em que viviam, Red, Chuck e Bomb se juntam em novas confusões, cada vez mais irritados.", movie.overview)
        assertEquals(LocalDate.parse("2019-10-03"), movie.releaseDate)
    }
}