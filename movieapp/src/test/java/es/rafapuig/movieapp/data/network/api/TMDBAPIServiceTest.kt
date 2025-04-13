package es.rafapuig.movieapp.data.network.api

import es.rafapuig.movieapp.trending.tvshows.data.TVShowRepositoryImpl
import es.rafapuig.movieapp.core.data.network.provider.TMDBNetworkProvider
import es.rafapuig.movieapp.movies.data.network.provider.MovieServiceProvider
import es.rafapuig.movieapp.trending.tvshows.data.network.TvShowsServiceProvider
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TMDBAPIServiceTest {

    @Test
    fun testFetchTrendingTVShows() {
        runTest {
            val retrofit = TMDBNetworkProvider.provideRetrofit()
            val service = TvShowsServiceProvider.provideService(retrofit)
            val repository = TVShowRepositoryImpl(service)

            val tvShows = repository.fetchTrendingTVShows()

            tvShows.forEach {
                println(it.name)
            }
        }
    }


}