package es.rafapuig.movieapp.data.network.api

import es.rafapuig.movieapp.data.TVShowRepositoryImpl
import es.rafapuig.movieapp.data.network.provider.TMDBNetworkProvider
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TMDBAPIServiceTest {

    @Test
    fun testFetchTrendingTVShows() {
        runTest {
            val service = TMDBNetworkProvider.getTheMovieDBApiService()
            val repository = TVShowRepositoryImpl(service)

            val tvShows = repository.fetchTrendingTVShows()

            tvShows.forEach {
                println(it.name)
            }
        }
    }


}