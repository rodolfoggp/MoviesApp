package com.rodolfogusson.testepag.viewmodel.details.favoritedetails

import androidx.lifecycle.LiveData
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.viewmodel.details.DetailsViewModel

class FavoriteDetailsViewModel(
    id: Int,
    favoritesRepository: FavoritesRepository
) : DetailsViewModel(id, favoritesRepository) {

    override val movie: LiveData<Movie> = favoritesRepository.getFavoriteById(id)
}