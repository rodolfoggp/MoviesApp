package com.rodolfogusson.testepag.infrastructure.data.repository

import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie

open class MoviesRepository(private val service: MoviesService) {

    private val movies = mutableListOf<Movie>()

    @Throws(Exception::class)
    open fun getMovies(genres: List<Genre>?): List<Movie>? {
        return service.getMovies(MoviesService.apiKey, "pt-BR")
            .execute().body()?.results
    }
}