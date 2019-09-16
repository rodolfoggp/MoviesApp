package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.lifecycle.*
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.data.Status
import com.rodolfogusson.testepag.infrastructure.data.combineLatest
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.viewmodel.movieslist.SortingOrder.*

class MoviesListViewModel(
    private val moviesRepository: MoviesRepository,
    genresRepository: GenresRepository
) : ViewModel() {

    private var pagesDisplayed = 0

    private val getGenresEvent = MutableLiveData<Unit>()

    val genres: LiveData<Resource<List<Genre>>> = Transformations.switchMap(getGenresEvent) {
        isLoading.value = true
        genresRepository.getGenres()
    }

    private val getMoviesEvent = MutableLiveData<Unit>()

    private val unsortedMovies: LiveData<Resource<List<Movie>>> =
        Transformations.switchMap(getMoviesEvent.combineLatest(genres)) {
            val genresResource = it.second
            if (genresResource.hasError) {
                MutableLiveData<Resource<List<Movie>>>().apply {
                    value = Resource.error(genresResource.error)
                }
            } else {
                genresResource.data?.let { genres ->
                    isLoading.value = true
                    moviesRepository.getMovies(genres, pagesDisplayed + 1)
                }
            }
        }

    private val sortingOrder =
        MutableLiveData<SortingOrder>().apply { value = ASCENDING }

    val movies: LiveData<Resource<List<Movie>>> =
        Transformations.map(unsortedMovies.combineLatest(sortingOrder)) {
            val moviesResource: Resource<List<Movie>> = it.first
            val order = it.second

            val moviesList = moviesResource.data?.let { list ->
                when (order) {
                    ASCENDING -> list.sortedBy { movie -> movie.releaseDate }
                    else -> list.sortedByDescending { movie -> movie.releaseDate }
                }
            }

            Resource(moviesResource.status, moviesList, moviesResource.error)
        }

    private val isLoading = MutableLiveData<Boolean>().apply { value = true }

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

    init {
        getGenresEvent.value = Unit
        getMoviesEvent.value = Unit
    }

    fun onMoviesLoaded(status: Status) {
        isLoading.value = false
        if (status == Status.SUCCESS) pagesDisplayed++
    }

    fun loadMoreMovies() {
        if (isLoading.value == false && pagesDisplayed < moviesRepository.getTotalPages) {
            isLoading.value = true
            getMoviesEvent.value = Unit
        }
    }

    fun retryGetMovies() {
        if (genres.value?.data?.isNotEmpty() == true) {
            if (isLoading.value == false) {
                isLoading.value = true
                getMoviesEvent.value = Unit
            }
        } else {
            getGenresEvent.value = Unit
        }
    }
}