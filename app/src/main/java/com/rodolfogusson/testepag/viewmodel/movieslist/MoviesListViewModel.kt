package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.lifecycle.*
import com.rodolfogusson.testepag.infrastructure.data.DoubleTrigger
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie


class MoviesListViewModel(moviesRepository: MoviesRepository, genresRepository: GenresRepository) :
    ViewModel() {

    val genres: LiveData<Resource<List<Genre>>> = genresRepository.getGenres()

    private val pagesDisplayed = MutableLiveData<Int>().apply { value = 1 }

    val movies: LiveData<Resource<List<Movie>>> =
        Transformations.switchMap(DoubleTrigger(genres, pagesDisplayed)) {
            it.first?.let { genresResource ->
                if (genresResource.hasError) {
                    MutableLiveData<Resource<List<Movie>>>().apply {
                        value = Resource.error(genresResource.error)
                    }
                } else {
                    genresResource.data?.let { genres ->
                        pagesDisplayed.value?.let { page ->
                            moviesRepository.getMovies(genres, page)
                        }
                    }
                }
            }
        }

    val isLoading = MutableLiveData<Boolean>().apply { value = true }

    fun loadMoreMovies() {
        pagesDisplayed.value = pagesDisplayed.value!! + 1
    }
}