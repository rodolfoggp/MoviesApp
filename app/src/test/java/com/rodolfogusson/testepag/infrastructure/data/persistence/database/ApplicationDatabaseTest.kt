package com.rodolfogusson.testepag.infrastructure.data.persistence.database

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.*
import org.junit.Test

class ApplicationDatabaseTest {

    @Test
    fun `database should be a singleton`() {
        val contextMock: Context = mock()
        val db1 = ApplicationDatabase.getInstance(contextMock)
        val db2 = ApplicationDatabase.getInstance(contextMock)
        assert(db1 === db2)
    }
}