package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.FavoriteDao

class FavoritesRepository(private val favoriteDao: FavoriteDao) {

    fun getFavorites() = favoriteDao.getAllFavorites()

    fun isFavorite(id: Int): LiveData<Boolean> =
        Transformations.map(favoriteDao.getFavoriteById(id)) {
            it != null
        }

    fun add(id: Int) = favoriteDao.add(id)

    fun remove(id: Int) = favoriteDao.remove(id)
}