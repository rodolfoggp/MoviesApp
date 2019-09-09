package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie
import java.lang.Exception

class MoviesListViewModel(moviesRepository: MoviesRepository, genresRepository: GenresRepository) :
    ViewModel() {

    val error = MutableLiveData<Boolean>()

    private val genres: LiveData<List<Genre>> = genresRepository.getGenres()

    val movies: LiveData<List<Movie>> = Transformations.switchMap(genres) { genres ->
        try {
            error.value = false
            moviesRepository.getMovies(genres)
        } catch (e: Exception) {
            error.value = true
            null
        }
    }
}