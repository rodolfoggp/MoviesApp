package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.infrastructure.service.callback
import com.rodolfogusson.testepag.model.Genre

open class GenresRepository(val service: MoviesService) {

    open fun getGenres(): LiveData<List<Genre>> {
        val liveData = MutableLiveData<List<Genre>>()
        service.getGenres(MoviesService.apiKey, "pt-BR").enqueue(callback {
            liveData.value = it?.body()?.genres
        })
        return liveData
    }
}