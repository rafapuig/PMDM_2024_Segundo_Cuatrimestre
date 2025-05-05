package es.rafapuig.movieapp.trending.tvshows.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.rafapuig.movieapp.TheMovieDBApplication
import es.rafapuig.movieapp.core.ui.util.getTvShowsRepository
import es.rafapuig.movieapp.trending.tvshows.domain.TvShowRepository
import es.rafapuig.movieapp.trending.tvshows.domain.model.TVShow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TrendingTvShowsViewModel(private val repository: TvShowRepository) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val tvShows : List<TVShow>) : UiState()
        data class Error(val exception: Throwable) : UiState()
    }

    private val _trendingTvShows : MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Loading) //MutableStateFlow(TvShowsUiState.Success(emptyList()) as TvShowsUiState)

    val trendingTvShows = _trendingTvShows.asStateFlow()

    init {
        fetchTrendingTvShows()
    }

    fun fetchTrendingTvShows() {
        viewModelScope.launch {
            try {
                delay(2.seconds)
                val list = repository.fetchTrendingTVShows()
                _trendingTvShows.value = UiState.Success(list)
            } catch (ex: Exception) {
                _trendingTvShows.value = UiState.Error(ex)
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
                TrendingTvShowsViewModel(this.getTvShowsRepository())
            }
        }
    }
}

