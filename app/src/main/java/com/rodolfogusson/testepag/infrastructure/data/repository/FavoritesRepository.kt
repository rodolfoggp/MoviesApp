package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.FavoriteDao
import com.rodolfogusson.testepag.model.Movie

open class FavoritesRepository private constructor(val favoriteDao: FavoriteDao) {

    fun getFavorites() = favoriteDao.getAllFavorites()

    fun isFavorite(id: Int): LiveData<Boolean> =
        Transformations.map(favoriteDao.getFavoriteById(id)) {
            it != null
        }

    fun add(movie: Movie) = favoriteDao.insert(movie)

    fun remove(movie: Movie) = favoriteDao.delete(movie)

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: FavoritesRepository? = null

        fun getInstance(favoriteDao: FavoriteDao) =
            instance ?: synchronized(this) {
                instance ?: FavoritesRepository(favoriteDao).also { instance = it }
            }
    }
}