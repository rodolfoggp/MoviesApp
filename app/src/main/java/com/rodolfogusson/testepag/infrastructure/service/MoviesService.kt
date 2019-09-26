package com.rodolfogusson.testepag.infrastructure.service

import com.google.gson.GsonBuilder
import com.rodolfogusson.testepag.infrastructure.service.deserializer.LocalDateDeserializer
import com.rodolfogusson.testepag.infrastructure.service.dto.GenresResponse
import com.rodolfogusson.testepag.infrastructure.service.dto.MoviesResponse
import org.threeten.bp.LocalDate
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


interface MoviesService {

    @GET("movie/upcoming")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Call<MoviesResponse>

    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<GenresResponse>

    companion object {
        val apiKey = "1f54bd990f1cdfb230adb312546d765d"
        private var baseUrl = BaseURL.MOVIES.url

        fun create(): MoviesService {
            val gson = GsonBuilder().apply {
                registerTypeAdapter(
                    LocalDate::class.java,
                    LocalDateDeserializer()
                )
            }.create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(MoviesService::class.java)
        }
    }
}