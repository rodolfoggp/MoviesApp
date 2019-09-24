package com.rodolfogusson.testepag.viewmodel.moviesdetails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodolfogusson.testepag.infrastructure.data.persistence.database.ApplicationDatabase
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.infrastructure.service.MoviesService

class MovieDetailsViewModelFactory(val context: Context, val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val service = MoviesService.create()
        val moviesRepository = MoviesRepository.getInstance(service)

        val database = ApplicationDatabase.getInstance(context)
        val favoriteDao = database.favoriteDao()
        val movieGenreJoinDao = database.movieGenreJoinDao()

        val favoritesRepository = FavoritesRepository.getInstance(favoriteDao, movieGenreJoinDao)
        return MovieDetailsViewModel(id, moviesRepository, favoritesRepository) as T
    }
}