package es.rafapuig.movieapp

import es.rafapuig.movieapp.core.data.network.provider.TMDBNetworkProvider
import es.rafapuig.movieapp.movies.data.network.provider.MovieServiceProvider
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieServiceTest {

    @Test
    fun testFetchNowPlayingMovies() {
        val retrofit = TMDBNetworkProvider.provideRetrofit()
        val service = MovieServiceProvider.provideService(retrofit)
        runTest {
            val response = service.getNowPlayingMovies()
            println(response.results)
        }

    }
}