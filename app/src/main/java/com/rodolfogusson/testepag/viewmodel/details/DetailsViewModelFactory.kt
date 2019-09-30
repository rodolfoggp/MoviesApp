package com.rodolfogusson.testepag.viewmodel.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodolfogusson.testepag.infrastructure.data.persistence.database.ApplicationDatabase
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.viewmodel.details.favoritedetails.FavoriteDetailsViewModel
import com.rodolfogusson.testepag.viewmodel.details.moviedetails.MovieDetailsViewModel

class DetailsViewModelFactory(val context: Context, val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val service = MoviesService.create()
        val moviesRepository = MoviesRepository.getInstance(service)

        val database = ApplicationDatabase.getInstance(context)
        val favoriteDao = database.favoriteDao()
        val movieGenreJoinDao = database.movieGenreJoinDao()

        val favoritesRepository = FavoritesRepository.getInstance(favoriteDao, movieGenreJoinDao)

        return when (modelClass) {
            MovieDetailsViewModel::class.java -> MovieDetailsViewModel(
                id,
                moviesRepository,
                favoritesRepository
            ) as T
            FavoriteDetailsViewModel::class.java -> FavoriteDetailsViewModel(
                id,
                favoritesRepository
            ) as T
            else -> throw Exception()
        }
    }
}