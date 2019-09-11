package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie

class MoviesListViewModel(moviesRepository: MoviesRepository, genresRepository: GenresRepository) :
    ViewModel() {

    val genres: LiveData<Resource<List<Genre>>> = genresRepository.getGenres()

    val movies: LiveData<Resource<List<Movie>>> = Transformations.switchMap(genres) {
        if (it.hasError) {
            MutableLiveData<Resource<List<Movie>>>().apply {
                value = Resource.error(it.error)
            }
        } else {
            it.data?.let { allGenres ->
                moviesRepository.getMovies(allGenres)
            }
        }
    }
}