package es.rafapuig.movieapp.trending.tvshows.ui.views

import es.rafapuig.movieapp.trending.tvshows.domain.model.TVShow

data class TrendingTvShowsUiState(
    val isLoading: Boolean = false,
    val tvShows: List<TVShow> = emptyList(),
    val error: String? = null
)