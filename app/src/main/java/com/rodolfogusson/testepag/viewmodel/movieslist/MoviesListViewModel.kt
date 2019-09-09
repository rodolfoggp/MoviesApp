package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Movie
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MoviesListViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    val movies = MutableLiveData<List<Movie>>()
    val error = MutableLiveData<Boolean>()

    init {
        getMovies()
    }

    fun getMovies() {
        GlobalScope.launch {
            try {
                val moviesList = moviesRepository.getMovies(listOf())
                movies.postValue(moviesList)
            } catch (e: Exception) {
                error.postValue(true)
            }
        }
    }
}