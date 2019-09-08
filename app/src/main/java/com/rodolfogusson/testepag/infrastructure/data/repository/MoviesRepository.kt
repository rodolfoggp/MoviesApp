package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.MutableLiveData
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.model.Movie

class MoviesRepository(private val service: MoviesService) {

    private val movies = MutableLiveData<List<Movie>>()

    @Throws(Exception::class)
    fun getMovies(): List<Movie>? {
        return service.getMovies(MoviesService.apiKey, "pt-BR")
            .execute()
            .body()?.results
    }
}