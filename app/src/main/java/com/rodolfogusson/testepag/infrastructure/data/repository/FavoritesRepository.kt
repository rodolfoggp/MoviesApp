package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.FavoriteDao
import com.rodolfogusson.testepag.model.Movie

class FavoritesRepository(private val favoriteDao: FavoriteDao) {

    fun getFavorites() = favoriteDao.getAllFavorites()

    fun isFavorite(id: Int): LiveData<Boolean> =
        Transformations.map(favoriteDao.getFavoriteById(id)) {
            it != null
        }

    fun add(movie: Movie) = favoriteDao.insert(movie)

    fun remove(movie: Movie) = favoriteDao.delete(movie)
}