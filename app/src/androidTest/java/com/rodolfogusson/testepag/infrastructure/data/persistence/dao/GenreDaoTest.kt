package com.rodolfogusson.testepag.infrastructure.data.persistence.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rodolfogusson.testepag.infrastructure.data.persistence.database.ApplicationDatabase
import com.rodolfogusson.testepag.model.Genre
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GenreDaoTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var db: ApplicationDatabase
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var genreDao: GenreDao
    private val genres: List<Genre> = listOf(
        Genre(1, "Aventura"),
        Genre(2, "Comédia"),
        Genre(3, "Ação"),
        Genre(4, "Drama"),
        Genre(5, "Suspense")
    )
    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase::class.java).build()
        genreDao = db.genreDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun daoShouldInsertAndGetGenreByIdCorrectly() {
        //WHEN
        genreDao.insert(genres[0])

        //THEN
        val liveData = genreDao.getGenreById(genres[0].id)
        liveData.observeForever {  }
        assertEquals(genres[0], liveData.value)
    }

    @Test
    fun daoShouldInsertAllAndGetAllCorrectly() {
        //WHEN
        genreDao.insertAll(genres)

        //THEN
        val liveData = genreDao.getAll()
        liveData.observeForever {  }
        assertEquals(genres, liveData.value)
    }
}