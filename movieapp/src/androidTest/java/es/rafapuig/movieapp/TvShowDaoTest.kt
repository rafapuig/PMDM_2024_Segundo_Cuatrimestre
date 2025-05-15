package es.rafapuig.movieapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import es.rafapuig.movieapp.core.data.network.provider.TMDBNetworkProvider
import es.rafapuig.movieapp.data.local.MoviesDatabase
import es.rafapuig.movieapp.data.local.entity.TvShowEntity
import es.rafapuig.movieapp.movies.data.mappers.toDatabase
import es.rafapuig.movieapp.trending.tvshows.data.api.TvShowsApiService
import es.rafapuig.movieapp.trending.tvshows.data.model.TrendingTVShowsResponse
import es.rafapuig.movieapp.trending.tvshows.data.network.TvShowsServiceProvider
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TvShowDaoTest {

    lateinit var db: MoviesDatabase
    lateinit var apiService: TvShowsApiService

    @Before
    fun setupDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MoviesDatabase::class.java).build()
    }

    @Before
    fun setupApiService() {
        val retrofit = TMDBNetworkProvider.provideRetrofit()
        apiService = TvShowsServiceProvider.provideService(retrofit)
    }

    @Test
    fun testTvShowDao() {
        val tvShowEntity1 = TvShowEntity(1, "Juego de Tronos", "Bla bla", "...")
        val tvShowEntity2 = TvShowEntity(2, "Los Simpson", "Bla bla bla", ".../...")
        val list = listOf(tvShowEntity1, tvShowEntity2)
        runTest {
            db.tvShowDao.insertAll(list)
            val tvShows = db.tvShowDao.getAll()
            tvShows.forEach {
                println(it)
            }
        }
    }

    @Test
    fun testInsertFromApi() = runTest {
        val deferredResponse = async { return@async apiService.fetchTrendingTVShows(page = 1) }
        val response = deferredResponse.await()

        launch {
            val tvShowsEntities = response.tvShowList.map { it.toDatabase() }
            db.tvShowDao.insertAll(tvShowsEntities)
        }

        println("Paginas totales: ${response.totalPages}")


        val list = mutableListOf<Deferred<TrendingTVShowsResponse>>()

        if(response.totalPages > 1) {
            var count = 2
            while (count <= 6 /* response.totalPages*/) {
                list.add(async { return@async apiService.fetchTrendingTVShows(page = count) })
                count++
            }
        }

        list.forEach {
            val tvShowsResponse = it.await()
            launch {
                val tvShowsEntities = tvShowsResponse.tvShowList.map { it.toDatabase() }
                db.tvShowDao.insertAll(tvShowsEntities)
            }
        }

        val tvShows = db.tvShowDao.getAll()
        tvShows.forEach { println(it) }

        println("Total: " + db.tvShowDao.getAll().count())
    }

}