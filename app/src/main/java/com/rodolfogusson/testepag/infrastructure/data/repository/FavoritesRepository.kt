package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.FavoriteDao
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.MovieGenreJoinDao
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.model.MovieGenreJoin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class FavoritesRepository private constructor(
    private val favoriteDao: FavoriteDao,
    private val movieGenreJoinDao: MovieGenreJoinDao,
    private val dispatcher: CoroutineDispatcher
) {

    fun getFavorites() = Transformations.switchMap(favoriteDao.getAllFavorites()) { moviesList ->
        val favorites = MutableLiveData<List<Movie>>()
        GlobalScope.launch(dispatcher) {
            for (movie in moviesList) {
                movie.genres = movieGenreJoinDao.getGenresForMovie(movie.id)
            }
            favorites.postValue(moviesList)
        }
        favorites
    }

    open fun getFavoriteById(id: Int): LiveData<Movie> =
        Transformations.switchMap(favoriteDao.getFavoriteById(id)) {
            val favorite = MutableLiveData<Movie>()
            GlobalScope.launch(dispatcher) {
                it?.genres = movieGenreJoinDao.getGenresForMovie(it.id)
                favorite.postValue(it)
            }
            favorite
        }

    fun isFavorite(id: Int): LiveData<Boolean> =
        Transformations.map(favoriteDao.getFavoriteById(id)) {
            it != null
        }

    fun add(movie: Movie) = GlobalScope.launch(dispatcher) {
        favoriteDao.insert(movie)
        movie.genres?.forEach { movieGenreJoinDao.insert(MovieGenreJoin(movie.id, it.id)) }
    }

    fun remove(movie: Movie) = GlobalScope.launch(dispatcher) {
        favoriteDao.delete(movie)
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: FavoritesRepository? = null

        fun getInstance(
            favoriteDao: FavoriteDao,
            movieGenreJoinDao: MovieGenreJoinDao,
            dispatcher: CoroutineDispatcher = Dispatchers.IO
        ) =
            instance ?: synchronized(this) {
                instance ?: FavoritesRepository(favoriteDao, movieGenreJoinDao, dispatcher)
                    .also { instance = it }
            }
    }
}