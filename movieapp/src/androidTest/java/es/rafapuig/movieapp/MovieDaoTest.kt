package es.rafapuig.movieapp

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import es.rafapuig.movieapp.data.local.MoviesDatabase
import es.rafapuig.movieapp.data.local.provider.MovieDatabaseProvider
import kotlinx.coroutines.test.runTest
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    lateinit var database: MoviesDatabase

    @Before
    fun setupDatabase() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        database = MovieDatabaseProvider.provideDatabase(appContext)
    }

    @Test
    fun testMovieDao() {
        // Context of the app under test.

        //println(appContext.filesDir)
        println(database.openHelper.databaseName)
        //appContext.deleteDatabase("movies.db")

        runTest {
            val result = database.movieDao.getMovieWithGenreDetails(447273)
            //val result = db.movieDao.getNowPlayingMovies()
            println(result.get(0).movie.title)
            println("Resultado $result")
            Log.i("RAFA", "$result")
        }
    }

    @After
    fun closeDatabase() {
        database.close()
    }

}