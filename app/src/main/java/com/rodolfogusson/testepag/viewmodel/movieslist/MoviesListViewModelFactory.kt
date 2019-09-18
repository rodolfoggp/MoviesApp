package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.infrastructure.service.MoviesService

class MoviesListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val service = MoviesService.create()
        val moviesRepository = MoviesRepository.getInstance(service)
        val genresRepository = GenresRepository.getInstance(service)
        return MoviesListViewModel(moviesRepository, genresRepository) as T
    }
}