package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.lifecycle.*
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie


class MoviesListViewModel(moviesRepository: MoviesRepository, genresRepository: GenresRepository) :
    ViewModel() {

    val genres: LiveData<Resource<List<Genre>>> = genresRepository.getGenres()

    /*val movies: LiveData<Resource<List<Movie>>> = Transformations.switchMap(genres) {
        isLoading.value = false
        if (it.hasError) {
            MutableLiveData<Resource<List<Movie>>>().apply {
                value = Resource.error(it.error)
            }
        } else {
            it.data?.let { allGenres ->
                moviesRepository.getMovies(allGenres)
            }
        }
    }*/

    private val page = MutableLiveData<Int>().apply { value = 1 }

    val movies: LiveData<Resource<List<Movie>>> = Transformations.switchMap(DoubleTrigger(genres, page)) {
        it.first?.let { resource ->
            if (resource.hasError) {
                MutableLiveData<Resource<List<Movie>>>().apply {
                    value = Resource.error(resource.error)
                }
            } else {
                resource.data?.let { allGenres ->
                    moviesRepository.getMovies(allGenres, page.value!!)
                }
            }
        }
    }

    val isLoading = MutableLiveData<Boolean>().apply { value = true }

    fun loadMoreMovies() {
        page.value = page.value!! + 1
    }
}


class DoubleTrigger<A, B>(a: LiveData<A>, b: LiveData<B>) : MediatorLiveData<Pair<A?, B?>>() {
    init {
        addSource(a) { value = it to b.value }
        addSource(b) { value = a.value to it }
    }
}