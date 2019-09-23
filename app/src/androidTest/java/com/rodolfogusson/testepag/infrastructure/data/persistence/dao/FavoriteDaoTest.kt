package com.rodolfogusson.testepag.infrastructure.data.persistence.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rodolfogusson.testepag.infrastructure.data.persistence.database.ApplicationDatabase
import com.rodolfogusson.testepag.model.Movie
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var db: ApplicationDatabase
    private lateinit var favoriteDao: FavoriteDao
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val moviesList = listOf(
        Movie(
            1,
            "Filme 1",
            "Descrição 1",
            LocalDate.parse("2019-05-12"),
            "/wF6SNPcUrTKFA4fOFfukm7zQ3ob.jpg",
            6.4,
            10
        ),
        Movie(
            2,
            "Filme 2",
            "Descrição 2",
            LocalDate.parse("2019-08-26"),
            "/foEOVg4HQl2VzKzTh27CAHmXyg.jpg",
            7.9,
            17
        ),
        Movie(
            3,
            "Filme 3",
            "Descrição 2",
            LocalDate.parse("2019-07-22"),
            "/foEOVg4HQl2VzKzTh27CAHmXyg.jpg",
            8.0,
            19
        )
    )

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
    fun daoShouldInsertAndGetAllCorrectly() {
        //GIVEN
        val favoritesLiveData = favoriteDao.getAllFavorites()
        favoritesLiveData.observeForever {  }
        assertEquals(favoritesLiveData.value?.size, 0)


        //WHEN
        favoriteDao.insert(moviesList[1])
        assertEquals(favoritesLiveData.value?.get(0), moviesList[1])
    }

    @Test
    fun daoShouldGetFavoriteByIdCorrectly() {
        //GIVEN
        favoriteDao.insert(moviesList[0])
        favoriteDao.insert(moviesList[1])
        favoriteDao.insert(moviesList[2])

        //WHEN
        val favoriteLiveData = favoriteDao.getFavoriteById(moviesList[1].id)
        favoriteLiveData.observeForever {  }

        //THEN
        val favorite = favoriteLiveData.value
        assertEquals(moviesList[1], favorite)
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