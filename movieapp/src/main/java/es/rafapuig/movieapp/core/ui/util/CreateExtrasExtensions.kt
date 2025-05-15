package es.rafapuig.movieapp.core.ui.util

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import es.rafapuig.movieapp.TheMovieDBApplication
import es.rafapuig.movieapp.movies.domain.MovieRepository
import es.rafapuig.movieapp.trending.tvshows.domain.TvShowRepository


fun CreationExtras.getMoviesRepository(): MovieRepository {
    val application = this[APPLICATION_KEY] as TheMovieDBApplication
    return application.moviesRepository
}

fun CreationExtras.getTvShowsRepository(): TvShowRepository {
    val application = this[APPLICATION_KEY] as TheMovieDBApplication
    return application.tvShowRepository
}

fun CreationExtras.getApplication(): TheMovieDBApplication  =
    this[APPLICATION_KEY] as TheMovieDBApplication

