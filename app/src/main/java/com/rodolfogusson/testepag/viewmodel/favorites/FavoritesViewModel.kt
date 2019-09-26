package com.rodolfogusson.testepag.viewmodel.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.model.Movie

class FavoritesViewModel(private val favoritesRepository: FavoritesRepository) : ViewModel() {
    val favorites: LiveData<List<Movie>> = favoritesRepository.getFavorites()
}