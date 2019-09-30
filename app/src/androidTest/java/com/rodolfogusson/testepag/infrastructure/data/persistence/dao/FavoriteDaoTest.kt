package com.rodolfogusson.testepag.infrastructure.data.persistence.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rodolfogusson.testepag.TestHelper
import com.rodolfogusson.testepag.infrastructure.data.persistence.database.ApplicationDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var db: ApplicationDatabase
    private lateinit var favoriteDao: FavoriteDao
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val testHelper = TestHelper()
    private val moviesList = testHelper.moviesList

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase::class.java).build()
        favoriteDao = db.favoriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun daoShouldInsertAndGetFavoriteByIdCorrectly() {
        //WHEN
        val movie = moviesList[0]
        favoriteDao.insert(movie)
        val favoriteLiveData = favoriteDao.getFavoriteById(movie.id)
        favoriteLiveData.observeForever {  }

        //THEN
        val movieFromDb = favoriteLiveData.value
        assertEquals(movie, movieFromDb)
    }

    @Test
    fun daoShouldInsertManyAndGetAllCorrectly() {
        //GIVEN
        moviesList.forEach { favoriteDao.insert(it) }

        //WHEN
        val favoritesLiveData = favoriteDao.getAllFavorites()
        favoritesLiveData.observeForever {  }

        //THEN
        val moviesFromDb = favoritesLiveData.value
        assertEquals(moviesList, moviesFromDb)
    }

    @Test
    fun daoShouldDeleteCorrectly() {
        //GIVEN
        favoriteDao.insert(moviesList[0])
        favoriteDao.insert(moviesList[1])
        favoriteDao.insert(moviesList[2])

        //WHEN
        favoriteDao.delete(moviesList[1])
        val favoritesLiveData = favoriteDao.getAllFavorites()
        favoritesLiveData.observeForever {  }

        //THEN
        val favorites = favoritesLiveData.value
        assertEquals(favorites?.size, 2)
        assertEquals(favorites, listOf(moviesList[0], moviesList[2]))
    }
}