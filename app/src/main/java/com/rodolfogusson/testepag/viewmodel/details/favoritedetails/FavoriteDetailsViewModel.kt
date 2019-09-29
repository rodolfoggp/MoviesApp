package com.rodolfogusson.testepag.viewmodel.details.favoritedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.viewmodel.details.DetailsViewModel

class FavoriteDetailsViewModel(
    id: Int,
    favoritesRepository: FavoritesRepository
) : DetailsViewModel(id, favoritesRepository) {

    private var lastLoadedMovie: Movie? = null

    private val movieFromRepository: LiveData<Movie> = favoritesRepository.getFavoriteById(id)

    override val movie: LiveData<Movie> = Transformations.map(movieFromRepository) {
        if (it != null) {
            lastLoadedMovie = it
            it
        } else {
            lastLoadedMovie
        }
    }
}