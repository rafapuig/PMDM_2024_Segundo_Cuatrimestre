package es.rafapuig.movieapp.trending.tvshows.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.rafapuig.movieapp.TheMovieDBApplication
import es.rafapuig.movieapp.core.data.network.provider.TMDBNetworkProvider
import es.rafapuig.movieapp.core.ui.util.getTvShowsRepository
import es.rafapuig.movieapp.trending.tvshows.data.TVShowRepositoryImpl
import es.rafapuig.movieapp.trending.tvshows.domain.TvShowRepository
import es.rafapuig.movieapp.trending.tvshows.ui.TrendingTvShowsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrendingTvShowsViewModel(
    private val repository: TvShowRepository) : ViewModel() {

    private val _trendingTvShows = MutableStateFlow(TrendingTvShowsUiState())
    val trendingTvShows = _trendingTvShows.asStateFlow()

    fun fetchTrendingTvShows() {
        viewModelScope.launch {
            try {
                _trendingTvShows.update { uiState -> uiState.copy(isLoading = true) }

                val list = repository.fetchTrendingTVShows()
                _trendingTvShows.update { uiState ->
                    uiState.copy(tvShows = list, isLoading = false)
                }
            } catch (ex: Exception) {
                _trendingTvShows.update { uiState ->
                    uiState.copy(error = ex.message, isLoading = false)
                }
            }
        }
    }

    fun switchProgressBarState() {
        _trendingTvShows.update { it.copy(isLoading = !it.isLoading) }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TheMovieDBApplication)
                val repository = application.tvShowRepository
                TrendingTvShowsViewModel(repository)
            }
        }
    }
}
