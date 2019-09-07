package com.rodolfogusson.testepag.infrastructure.service

import com.rodolfogusson.testepag.infrastructure.service.dto.MoviesResponse
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
            val retrofit = Retrofit.Builder()
                .baseUrl(BaseURL.MOVIES.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(MoviesService::class.java)
        }
    }
}