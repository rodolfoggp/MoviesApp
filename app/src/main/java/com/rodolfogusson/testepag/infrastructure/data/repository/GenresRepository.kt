package com.rodolfogusson.testepag.infrastructure.data.repository

import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.model.Genre

open class GenresRepository(val service: MoviesService) {

    open fun getGenres(): List<Genre>? {
        return service.getGenres(MoviesService.apiKey, "pt-BR").execute().body()?.genres
    }
}