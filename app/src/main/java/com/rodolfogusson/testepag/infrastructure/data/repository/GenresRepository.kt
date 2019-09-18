package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.infrastructure.service.then
import com.rodolfogusson.testepag.model.Genre

open class GenresRepository private constructor(val service: MoviesService) {

    open fun getGenres(): LiveData<Resource<List<Genre>>> {
        val liveData = MutableLiveData<Resource<List<Genre>>>()
        service.getGenres(MoviesService.apiKey, "pt-BR").enqueue(
            then {
                liveData.value = Resource(it.status, it.data?.genres, it.error)
            }
        )
        return liveData
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: GenresRepository? = null

        fun getInstance(service: MoviesService) =
            instance ?: synchronized(this) {
                instance ?: GenresRepository(service).also { instance = it }
            }
    }
}