package com.rodolfogusson.testepag.viewmodel.moviesdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.infrastructure.service.MoviesService

class MovieDetailsViewModelFactory(val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val service = MoviesService.create()
        val moviesRepository = MoviesRepository.getInstance(service)
        return MovieDetailsViewModel(id, moviesRepository) as T
    }
}