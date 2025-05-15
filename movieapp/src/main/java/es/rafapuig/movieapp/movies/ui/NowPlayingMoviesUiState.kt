package es.rafapuig.movieapp.movies.ui

import es.rafapuig.movieapp.movies.domain.model.Movie

sealed class NowPlayingMoviesUiState {

    data object Loading : NowPlayingMoviesUiState()
    data object Empty : NowPlayingMoviesUiState()
    data class Error(val message: String) : NowPlayingMoviesUiState()

    data class Success(val movies: List<Movie>) : NowPlayingMoviesUiState()

}