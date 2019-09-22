package com.rodolfogusson.testepag.infrastructure.data.persistence.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rodolfogusson.testepag.model.Movie

class FavoriteDao {
    fun getAllFavorites(): LiveData<List<Movie>> = MutableLiveData()
    fun getFavoriteById(id: Int): LiveData<Movie> = MutableLiveData()
    fun add(id: Int) = null
    fun remove(id: Int) = null
}