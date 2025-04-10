package es.rafapuig.movieapp.ui

import es.rafapuig.movieapp.data.TVShowRepositoryImpl
import es.rafapuig.movieapp.data.network.provider.TMDBNetworkProvider
import es.rafapuig.movieapp.domain.TvShowRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TvShowsViewModelTest {

    @Test
    fun fetchTrendingTvShows() {

        val service = TMDBNetworkProvider.getTheMovieDBApiService()
        val repository : TvShowRepository = TVShowRepositoryImpl(service)
        val viewModel = TvShowsViewModel(repository)


        runTest {
            viewModel.fetchTrendingTvShows()

            viewModel.trendingTvShows.collectLatest { tvShows ->
                println(tvShows)
            }

            println(viewModel.trendingTvShows.value)

        }

    }
}