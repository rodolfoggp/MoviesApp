package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.infrastructure.service.then
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie

open class MoviesRepository private constructor(private val service: MoviesService) {

    private val movies = mutableListOf<Movie>()
    private var totalPages = 1
    val getTotalPages get() = totalPages

    open fun getMovies(genres: List<Genre>, page: Int): LiveData<Resource<List<Movie>>> {
        val liveData = MutableLiveData<Resource<List<Movie>>>()
        service.getMovies(MoviesService.apiKey, "pt-BR", page).enqueue(
            then {
                liveData.value = if (it.hasError) {
                    Resource.error(it.error, movies)
                } else {
                    it.data?.let { response ->
                        totalPages = response.totalPages
                        val newMovies = response.results.map { e -> e.toMovie(genres) }
                        movies += newMovies
                        Resource.success(movies.toList())
                    }
                }
            }
        )
        return liveData
    }

    open fun getMovieBy(id: Int): LiveData<Movie> = MutableLiveData()

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: MoviesRepository? = null

        fun getInstance(service: MoviesService) =
            instance ?: synchronized(this) {
                instance ?: MoviesRepository(service).also { instance = it }
            }
    }
}