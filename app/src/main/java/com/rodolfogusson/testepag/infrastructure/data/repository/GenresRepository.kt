package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.model.Genre

open class GenresRepository(val service: MoviesService) {

    open fun getGenres(): LiveData<List<Genre>> {
        val genres = service.getGenres(MoviesService.apiKey, "pt-BR").execute().body()?.genres
        return MutableLiveData<List<Genre>>().apply { value = genres }
    }
}