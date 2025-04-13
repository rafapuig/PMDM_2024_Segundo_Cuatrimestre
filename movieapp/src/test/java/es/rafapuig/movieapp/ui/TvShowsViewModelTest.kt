package es.rafapuig.movieapp.ui

import es.rafapuig.movieapp.trending.tvshows.data.TVShowRepositoryImpl
import es.rafapuig.movieapp.core.data.network.provider.TMDBNetworkProvider
import es.rafapuig.movieapp.movies.data.network.provider.MovieServiceProvider
import es.rafapuig.movieapp.trending.tvshows.data.network.TvShowsServiceProvider
import es.rafapuig.movieapp.trending.tvshows.domain.TvShowRepository
import es.rafapuig.movieapp.trending.tvshows.ui.TvShowsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TvShowsViewModelTest {

    @Test
    fun fetchTrendingTvShows() {

        val retrofit = TMDBNetworkProvider.provideRetrofit()
        val service = TvShowsServiceProvider.provideService(retrofit)
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