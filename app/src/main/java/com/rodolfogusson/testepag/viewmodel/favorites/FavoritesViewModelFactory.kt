package com.rodolfogusson.testepag.viewmodel.favorites

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodolfogusson.testepag.infrastructure.data.persistence.database.ApplicationDatabase
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository

class FavoritesViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val database = ApplicationDatabase.getInstance(context)
        val favoriteDao = database.favoriteDao()
        val movieGenreJoinDao = database.movieGenreJoinDao()

        val favoritesRepository = FavoritesRepository.getInstance(favoriteDao, movieGenreJoinDao)
        return FavoritesViewModel(favoritesRepository) as T
    }
}