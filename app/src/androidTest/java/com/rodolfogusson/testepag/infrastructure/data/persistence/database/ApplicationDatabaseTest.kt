package com.rodolfogusson.testepag.infrastructure.data.persistence.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ApplicationDatabaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var db: ApplicationDatabase

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase::class.java).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun databaseShouldBeASingleton() {
        val db1 = ApplicationDatabase.getInstance(context)
        val db2 = ApplicationDatabase.getInstance(context)
        assertEquals(true, db1 === db2)
    }
}
