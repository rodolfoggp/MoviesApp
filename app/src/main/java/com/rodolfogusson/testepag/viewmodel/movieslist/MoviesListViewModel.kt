package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.lifecycle.*
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.data.combineLatest
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie


class MoviesListViewModel(
    private val moviesRepository: MoviesRepository,
    genresRepository: GenresRepository
) : ViewModel() {

    val genres: LiveData<Resource<List<Genre>>> = genresRepository.getGenres()

    private val pagesDisplayed = MutableLiveData<Int>().apply { value = 1 }

    val movies: LiveData<Resource<List<Movie>>> =
        Transformations.switchMap(genres.combineLatest(pagesDisplayed)) {
            val genresResource = it.first
            val page = it.second
            if (genresResource.hasError) {
                MutableLiveData<Resource<List<Movie>>>().apply {
                    value = Resource.error(genresResource.error)
                }
            } else {
                genresResource.data?.let { genres ->
                    isLoading.value = true
                    moviesRepository.getMovies(genres, page)
                }
            }

        }

    val isLoading = MutableLiveData<Boolean>().apply { value = true }

    val isProgressVisible: LiveData<Boolean> =
        Transformations.map(isLoading) {
            val isLoading = it
            val noMoviesHaveBeenLoaded = movies.value?.data?.isEmpty() ?: true
            isLoading && noMoviesHaveBeenLoaded
        }

    val isNextPageProgressVisible: LiveData<Boolean> =
        Transformations.map(isLoading) {
            val isLoading = it
            val someMoviesHaveBeenLoaded = movies.value?.data?.isNotEmpty() ?: false
            isLoading && someMoviesHaveBeenLoaded
        }

    fun loadMoreMovies() {
        pagesDisplayed.value?.let { pages ->
            if (pages < moviesRepository.getTotalPages) {
                pagesDisplayed.value = pages + 1
            }
        }
    }

    fun retryGetMovies() {
        // Triggers movies liveData to fetch this same page again
        pagesDisplayed.value = pagesDisplayed.value
    }
}