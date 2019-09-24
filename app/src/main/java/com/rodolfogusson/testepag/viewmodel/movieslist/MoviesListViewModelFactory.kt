package com.rodolfogusson.testepag.viewmodel.movieslist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodolfogusson.testepag.infrastructure.data.persistence.database.ApplicationDatabase
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.infrastructure.service.MoviesService

class MoviesListViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val service = MoviesService.create()

        val moviesRepository = MoviesRepository.getInstance(service)

        val database = ApplicationDatabase.getInstance(context)
        val genreDao = database.genreDao()
        val genresRepository = GenresRepository.getInstance(service, genreDao)

        return MoviesListViewModel(moviesRepository, genresRepository) as T
    }
}