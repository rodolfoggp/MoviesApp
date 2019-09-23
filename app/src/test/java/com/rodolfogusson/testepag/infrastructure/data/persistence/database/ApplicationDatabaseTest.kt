
import android.content.Context
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import com.nhaarman.mockitokotlin2.whenever
import com.rodolfogusson.testepag.infrastructure.data.persistence.database.ApplicationDatabase

class ApplicationDatabaseTest {

    @Mock
    lateinit var contextMock: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(contextMock.applicationContext).thenReturn(contextMock)
    }

    @Test
    fun `database should be a singleton`() {
        val db1 = ApplicationDatabase.getInstance(contextMock)
        val db2 = ApplicationDatabase.getInstance(contextMock)
        assert(db1 === db2)
    }
}
