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
    private var pages = 1

    open fun getMovies(genres: List<Genre>, page: Int): LiveData<Resource<List<Movie>>> {
        val liveData = MutableLiveData<Resource<List<Movie>>>()
        service.getMovies(MoviesService.apiKey, "pt-BR", page).enqueue(
            then {
                liveData.value = if (it.hasError) {
                    Resource.error(it.error)
                } else {
                    it.data?.let { response ->
                        pages = response.totalPages
                        val newMovies = response.results.map { e -> e.toMovie(genres) }
                        movies += newMovies
                        Resource.success(movies.toList())

                    }
                }
            }
        )
        return liveData
    }

    val getTotalPages get() = pages
}