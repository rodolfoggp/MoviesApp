package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.infrastructure.service.then
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie

open class MoviesRepository(private val service: MoviesService) {

    private val movies = mutableListOf<Movie>()

    @Throws(Exception::class)
    open fun getMovies(genres: List<Genre>): LiveData<Resource<List<Movie>>> {
        val liveData = MutableLiveData<Resource<List<Movie>>>()
        service.getMovies(MoviesService.apiKey, "pt-BR").enqueue(
            then {
                liveData.value = if (it.hasError) {
                    Resource.error(it.error)
                } else {
                    it.data?.results?.let{ elements ->
                        val movies = elements.map { e -> e.toMovie(genres) }
                        Resource.success(movies)
                    }
                }
            }
        )
        return liveData
    }
}