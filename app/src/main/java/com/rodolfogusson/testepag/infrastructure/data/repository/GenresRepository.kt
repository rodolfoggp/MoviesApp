package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.infrastructure.service.then
import com.rodolfogusson.testepag.model.Genre

open class GenresRepository(val service: MoviesService) {

    open fun getGenres(): LiveData<Resource<List<Genre>>> {
        val liveData = MutableLiveData<Resource<List<Genre>>>()
        service.getGenres(MoviesService.apiKey, "pt-BR").enqueue(
            then {
                liveData.value = if (it.hasError) {
                    Resource.error(it.error)
                } else {
                    Resource.success(it.data?.genres)
                }
            }
        )
        return liveData
    }
}