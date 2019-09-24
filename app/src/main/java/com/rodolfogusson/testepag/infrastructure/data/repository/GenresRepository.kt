package com.rodolfogusson.testepag.infrastructure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.GenreDao
import com.rodolfogusson.testepag.infrastructure.service.MoviesService
import com.rodolfogusson.testepag.infrastructure.service.then
import com.rodolfogusson.testepag.model.Genre
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class GenresRepository private constructor(
    val service: MoviesService,
    private val genreDao: GenreDao,
    private val dispatcher: CoroutineDispatcher
) {

    open fun getGenres(): LiveData<Resource<List<Genre>>> {
        val liveData = MutableLiveData<Resource<List<Genre>>>()
        service.getGenres(MoviesService.apiKey, "pt-BR").enqueue(
            then {
                it.data?.genres?.let { genres ->
                    GlobalScope.launch(dispatcher) { genreDao.insertAll(genres) }
                }
                liveData.value = Resource(it.status, it.data?.genres, it.error)
            }
        )
        return liveData
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: GenresRepository? = null

        fun getInstance(
            service: MoviesService,
            genreDao: GenreDao,
            dispatcher: CoroutineDispatcher = Dispatchers.IO
        ) =
            instance ?: synchronized(this) {
                instance ?: GenresRepository(service, genreDao, dispatcher)
                    .also { instance = it }
            }
    }
}