package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.infrastructure.service.callback
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie

open class MoviesRepository(private val service: MoviesService) {

    private val movies = mutableListOf<Movie>()

    @Throws(Exception::class)
    open fun getMovies(genres: List<Genre>): LiveData<List<Movie>> {
        val liveData = MutableLiveData<List<Movie>>()
        service.getMovies(MoviesService.apiKey, "pt-BR").enqueue(callback { response ->
            val elements = response?.body()?.results
            liveData.value = elements?.map { it.toMovie(genres) }
        })
        return liveData
    }
}