package es.rafapuig.movieapp.trending.tvshows.ui.views

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TrendingTvShowsViewModel {

    private val _trendingTvShows = MutableStateFlow(TrendingTvShowsUiState())
    val trendingTvShows = _trendingTvShows.asStateFlow()

    fun fetchTrendingTvShows() {
        _trendingTvShows.update { uiState -> uiState.copy(isLoading = true) }
    }

    fun switchProgressBarState() {
        _trendingTvShows.update { it.copy(isLoading = !it.isLoading) }
    }
}
