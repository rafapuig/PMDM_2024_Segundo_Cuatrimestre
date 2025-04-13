package es.rafapuig.movieapp.trending.tvshows.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.rafapuig.movieapp.TheMovieDBApplication
import es.rafapuig.movieapp.trending.tvshows.domain.TvShowRepository
import es.rafapuig.movieapp.trending.tvshows.domain.model.TVShow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TvShowsViewModel(private val repository: TvShowRepository) : ViewModel() {

    private val _trendingTvShows : MutableStateFlow<TvShowsUiState> =
        MutableStateFlow(TvShowsUiState.Loading) //MutableStateFlow(TvShowsUiState.Success(emptyList()) as TvShowsUiState)

    val trendingTvShows = _trendingTvShows.asStateFlow()

    init {
        fetchTrendingTvShows()
    }

    fun fetchTrendingTvShows() {
        viewModelScope.launch {
            try {
                delay(2.seconds)
                val list = repository.fetchTrendingTVShows()
                _trendingTvShows.value = TvShowsUiState.Success(list)
            } catch (ex: Exception) {
                _trendingTvShows.value = TvShowsUiState.Error(ex)
            }

        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TheMovieDBApplication)
                val repository = application.tvShowRepository
                //val service  = TMDBNetworkProvider.getTheMovieDBApiService()
                //val repository = TVShowRepositoryImpl(service)
                TvShowsViewModel(repository)
            }
        }
    }
}

sealed class TvShowsUiState {
    data object Loading : TvShowsUiState()
    data class Success(val tvShows : List<TVShow>) : TvShowsUiState()
    data class Error(val exception: Throwable) : TvShowsUiState()
}