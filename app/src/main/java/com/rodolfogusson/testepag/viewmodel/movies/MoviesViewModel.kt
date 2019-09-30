package com.rodolfogusson.testepag.viewmodel.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.data.Status
import com.rodolfogusson.testepag.infrastructure.data.combineLatest
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.viewmodel.movies.SortingOrder.*

class MoviesViewModel(
    private val moviesRepository: MoviesRepository,
    genresRepository: GenresRepository
) : ViewModel() {

    private var pagesDisplayed = 0

    val orderArray = arrayOf(UNSORTED, ASCENDING, DESCENDING)

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
        MutableLiveData<SortingOrder>().apply { value = UNSORTED }

    val movies: LiveData<Resource<List<Movie>>> =
        Transformations.map(unsortedMovies.combineLatest(sortingOrder)) {
            val moviesResource: Resource<List<Movie>> = it.first
            val order = it.second

            val moviesList = moviesResource.data?.let { list ->
                when (order) {
                    ASCENDING -> list.sortedBy { movie -> movie.releaseDate }
                    DESCENDING -> list.sortedByDescending { movie -> movie.releaseDate }
                    else -> list
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

    fun onMoviesUpdated(status: Status) {
        if (isLoading.value == true) {
            // New movies were loaded.
            isLoading.value = false
            if (status == Status.SUCCESS) pagesDisplayed++
        }
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

    fun onSortingSelected(position: Int) {
        val order = orderArray[position]
        sortingOrder.value = order
    }
}