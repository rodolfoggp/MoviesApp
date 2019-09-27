package com.rodolfogusson.testepag.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.model.Movie

abstract class DetailsViewModel(
    val id: Int,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    abstract val movie: LiveData<Movie>

    val isFavorite: LiveData<Boolean> = favoritesRepository.isFavorite(id)

    fun onFavoriteButtonClicked() {
        isFavorite.value?.let { isFavorite ->
            movie.value?.let { movie ->
                if (isFavorite) {
                    favoritesRepository.remove(movie)
                } else {
                    favoritesRepository.add(movie)
                }
            }
        }
    }
}