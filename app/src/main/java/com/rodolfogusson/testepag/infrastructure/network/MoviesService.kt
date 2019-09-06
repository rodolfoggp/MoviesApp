package com.rodolfogusson.testepag.infrastructure.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MoviesService {

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