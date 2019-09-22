package com.rodolfogusson.testepag.viewmodel.moviesdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.FavoriteDao
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.infrastructure.service.MoviesService

class MovieDetailsViewModelFactory(val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val service = MoviesService.create()
        val moviesRepository = MoviesRepository.getInstance(service)
        val favoriteDao = FavoriteDao()
        val favoritesRepository = FavoritesRepository(favoriteDao)
        return MovieDetailsViewModel(id, moviesRepository, favoritesRepository) as T
    }
}