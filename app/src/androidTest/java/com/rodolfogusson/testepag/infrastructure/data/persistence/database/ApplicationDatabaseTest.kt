import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import org.junit.Before
import org.junit.Test
import com.rodolfogusson.testepag.infrastructure.data.persistence.database.ApplicationDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.FavoriteDao
import com.rodolfogusson.testepag.model.Movie
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ApplicationDatabaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var db: ApplicationDatabase

    private val context = ApplicationProvider.getApplicationContext<Context>()

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
