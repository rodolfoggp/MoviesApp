package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodolfogusson.testepag.infrastructure.repository.MoviesRepository

class MoviesListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val moviesRepository = MoviesRepository()
        return MoviesListViewModel(moviesRepository) as T
    }
}