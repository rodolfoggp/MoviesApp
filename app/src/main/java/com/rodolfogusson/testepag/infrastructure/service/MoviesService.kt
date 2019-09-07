package com.rodolfogusson.testepag.infrastructure.service

import com.google.gson.GsonBuilder
import com.rodolfogusson.testepag.infrastructure.service.deserializer.LocalDateDeserializer
import com.rodolfogusson.testepag.infrastructure.service.dto.MoviesResponse
import org.threeten.bp.LocalDate
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/upcoming")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Call<MoviesResponse>

    companion object {
        fun create(): MoviesService {
            val gson = GsonBuilder().apply {
                registerTypeAdapter(
                    LocalDate::class.java,
                    LocalDateDeserializer()
                )
            }.create()

            val retrofit = Retrofit.Builder()
                .baseUrl(BaseURL.MOVIES.url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(MoviesService::class.java)
        }
    }
}